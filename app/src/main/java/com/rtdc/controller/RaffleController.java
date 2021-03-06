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
import org.springframework.data.domain.Page;
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
	public String list(Model model, @PageableDefault Pageable pageable) {
		
		List<Raffle> raffleList = raffleService.getRaffleList();
		List<Integer> cntList = new ArrayList<Integer>(); 
		List<Float> rateList = new ArrayList<Float>();
		List<List<RaffleTarget>> winnerList = new ArrayList<List<RaffleTarget>>();
		for(int i=0; i<raffleList.size(); i++) {
			int cnt = raffleTargetService.getRaffleTargetCnt(raffleList.get(i));
			cntList.add(i, cnt);
			rateList.add(i, (float)cnt/raffleList.get(i).getWinnerCnt());
			winnerList.add(raffleTargetService.getRaffleTargetWinnerList(raffleList.get(i), "Y"));
		}
		
		model.addAttribute("raffleList", raffleList);
		model.addAttribute("cntList", cntList);
		model.addAttribute("rateList", rateList);
		model.addAttribute("winnerList", winnerList);
		
		return "raffle/list";
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
		
		//?????? ???????????? ???????????????
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
		
		//?????? ???????????? ???????????????
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		User user = userService.getUser(userName);
		String role = user.getRole();
		
		if(!"ADMIN".equals(role)) {
			return "-9";
		}else {
			long raffleId = Long.parseLong((String)param.get("raffleId"));
			
			//Raffle ??????
			Raffle raffle = raffleService.getRaffle(raffleId);
			raffle.setStatus("1");
			raffleService.save(raffle);
			
			//????????? ??????
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

