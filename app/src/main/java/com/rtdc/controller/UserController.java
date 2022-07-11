
package com.rtdc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rtdc.model.Holder;
import com.rtdc.model.User;
import com.rtdc.service.HolderService;
import com.rtdc.service.PointHistoryService;
import com.rtdc.service.UserService;
import com.rtdc.validator.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PointHistoryService pointHistoryService;
	
	@Autowired
	private HolderService holderService;
	
	@RequestMapping("/login")
	public String login(Model model) {
		return "user/login";
	}
	
	@GetMapping("/signup")
	public String form(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "user/form";
	}
	
	@PostMapping("/signup")
	public String signUp(@Valid User user, BindingResult bindingResult) {
		
		//validate
		userValidator.validate(user, bindingResult);
		if(bindingResult.hasErrors()) {
			return "user/form";
		}
		
		//회원가입
		user = userService.signup(user);
		
		//회원가입 시 +1000p
		int point = pointHistoryService.savePoint(user, 1000, "회원가입");
		userService.savePoint(user, point);
		
		return "user/login";
	}
	
	@PostMapping("/holderCheck")
	@ResponseBody
	public String holderCheck(@RequestParam HashMap<String,Object> param) throws IOException {
		
		String result = "";
		
		try {
			String accounts = (String) param.get("accounts");
				
			URL url = new URL("https://th-api.klaytnapi.com/v2/contract/nft/0x46dbdc7965cf3cd2257c054feab941a05ff46488/owner/"+accounts+"?size=1000"); // MTDZ 갯수 : 120
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-chain-id", "8217");
			conn.setRequestProperty("Authorization", "Basic S0FTS0pWWUJFOE1YSjJIMTNINDVRODlaOl9IY181Q2pWeGdOSHJfY3psc1EzMm5HMUE0cy1rQ09tdmhRTXpNZWQ=");
			
			StringBuffer sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while(br.ready()) {
				sb.append(br.readLine());
			}
			
			JsonParser jsonParser = new JsonParser();
			JsonObject obj = jsonParser.parse(sb.toString()).getAsJsonObject();
			JsonArray arr = obj.get("items").getAsJsonArray();
			
			
			//현재 로그인한 사용자정보
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userName = auth.getName();
			
			User user = userService.getUser(userName);
			String gb = "MTDZ";
			
			for(int i=0; i<arr.size(); i++) {
				String tokenId = arr.get(i).getAsJsonObject().get("tokenId") + "";
				tokenId = tokenId.replace("\"", "");
				
				//해당 tokenId 이전 사용자 홀더 정보 삭제
				Holder holder = holderService.getHolder(gb, tokenId);
				if(holder != null) {
					holder.setDelYn("Y");
					holderService.save(holder);
					
					//이전 사용자 홀더정보가 0개 이하 일 경우 HOLDER ROLE 박탈
					User preUser = holder.getUser();
					List<Holder> list = holderService.getHolderList(gb, preUser);
					if(list.size() < 1) {
						preUser.setRole("USER");
						userService.save(preUser);
					}
				}
				
				//새로 인증한 홀더 정보 저장
				holder = new Holder();
				holder.setGb(gb);
				holder.setUser(user);
				holder.setTokenId(tokenId);
				holder.setDelYn("N");
				holderService.save(holder);
			}
			
			//HOLDER ROLE 부여
			List<Holder> list = holderService.getHolderList(gb, user);
			if(list.size() > 0) {
				user.setRole("HOLDER");
				userService.save(user);
				result = "1";
			}else {
				result = "-9";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
