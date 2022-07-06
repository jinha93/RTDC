package com.rtdc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rtdc.model.Raffle;
import com.rtdc.model.RaffleTarget;
import com.rtdc.model.User;
import com.rtdc.service.RaffleService;
import com.rtdc.service.RaffleTargetService;
import com.rtdc.service.UserService;

@Controller
@RequestMapping("/raffle")
public class RaffleController {
	
	@Autowired
	private RaffleService raffleService;
	
	@Autowired
	private RaffleTargetService raffleTargetService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/list")
	public String list(Model model, @PageableDefault Pageable pageable, @RequestParam String status) {
		
//		model.addAttribute("status", status);
		model.addAttribute("raffleList", raffleService.getRaffleList(pageable));
		
		return "raffle/list";
	}
	
	@GetMapping("/test")
	public String test(Model model) throws IOException {
		try {
//			URL url = new URL("https://th-api.klaytnapi.com/v2/contract/nft/0x46dbdc7965cf3cd2257c054feab941a05ff46488/owner/0x6b56517ce3D425b693017F43C45d74dBBf5BcF64?size=1000"); // MTDZ 갯수 : 0
			URL url = new URL("https://th-api.klaytnapi.com/v2/contract/nft/0x46dbdc7965cf3cd2257c054feab941a05ff46488/owner/0xd48dd5acce0a627019204079617cdab3fc78af7c?size=1000"); // MTDZ 갯수 : 120
//			URL url = new URL("https://th-api.klaytnapi.com/v2/contract/nft/0x46dbdc7965cf3cd2257c054feab941a05ff46488/owner/0x850F09C020e964FC09F835d26813b5A99e8c6C09?size=1000"); // MTDZ 갯수 : 1
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
			System.out.println(arr.toString());
			System.out.println(arr.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "index";
	}
	
	@GetMapping("/form")
	public String form(Model model, @RequestParam(required = false) Long raffleId) {
		
		if(raffleId == null) {
			Raffle raffle = new Raffle();
			model.addAttribute("raffle", raffle);
		}else {
			model.addAttribute("raffle", raffleService.getRaffle(raffleId));
		}
		return "raffle/form";
	}
	
	@PostMapping("/form")
	public String form(@Valid Raffle raffle, String startDateVal, String endDateVal) {
		
		//현재 로그인한 사용자정보
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		User user = userService.getUser(userName);
		raffle.setUser(user);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		startDateVal = startDateVal.replaceAll("-", "") + "000000";
		endDateVal = endDateVal.replaceAll("-", "") + "000000";
		
		raffle.setStartDateTime(LocalDateTime.parse(startDateVal, dtf));
		raffle.setEndDateTime(LocalDateTime.parse(endDateVal, dtf));
		
		raffle.setStatus("0");
		
		raffleService.save(raffle);
		
		return "redirect:/raffle/list";
	}
	
	@ResponseBody
	@PostMapping("/end")
	public String end(@RequestParam HashMap<String,Object> param) {
		
		//현재 로그인한 사용자정보
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		User user = userService.getUser(userName);
		String role = user.getRole();
		
		if(!"ADMIN".equals(role)) {
			return "-9";
		}else {
			long raffleId = Long.parseLong((String)param.get("raffleId"));
			
			//Raffle 종료
			Raffle raffle = raffleService.getRaffle(raffleId);
			raffle.setStatus("1");
			raffleService.save(raffle);
			
			//당첨자 추첨
			int winnerCnt = raffle.getWinnerCnt();
			List<RaffleTarget> list = raffleTargetService.getRaffleTargetList(raffle);
			List<RaffleTarget> winnerList = new ArrayList<RaffleTarget>(); 
			
			if(list.size() > 0) {
				Random random = new Random();
				for(int i=0; i<winnerCnt; i++) {
					winnerList.add(list.get(random.nextInt(list.size())));
				}
			}
			
			
			for(int i=0; i<winnerList.size(); i++) {
				RaffleTarget raffleTarget = winnerList.get(i);
				raffleTarget.setWinnerYn("Y");
				raffleTargetService.save(raffleTarget);
			}
			System.out.println(winnerList.toString());
		}
		
		return "1";
	}
	
	
	
}

