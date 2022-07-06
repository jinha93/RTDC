package com.rtdc.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rtdc.model.RaffleTarget;
import com.rtdc.model.User;
import com.rtdc.service.RaffleService;
import com.rtdc.service.RaffleTargetService;
import com.rtdc.service.UserService;

@Controller
@RequestMapping("/raffleTarget")
public class RaffleTargetController {
	
	@Autowired
	private RaffleTargetService raffleTargetService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RaffleService raffleService;
	
	@ResponseBody
	@PostMapping("/form")
	public String form(@RequestParam HashMap<String,Object> param) {
		
		RaffleTarget raffleTarget = new RaffleTarget();
		
		//현재 로그인한 사용자정보
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		User user = userService.getUser(userName);
		raffleTarget.setUser(user);
		
		Long raffleId = Long.parseLong((String) param.get("raffleId"));
		raffleTarget.setRaffle(raffleService.getRaffle(raffleId));
		raffleTarget.setTwitterHandle((String) param.get("twitterHandle"));
		raffleTarget.setDiscordHandle((String) param.get("discordHandle"));
		raffleTarget.setWinnerYn("N");
		
		raffleTargetService.save(raffleTarget);
		
		return "1";
	}
}
