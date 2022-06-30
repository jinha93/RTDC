package com.rtdc.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

import com.rtdc.model.Raffle;
import com.rtdc.model.User;
import com.rtdc.service.RaffleService;
import com.rtdc.service.UserService;

@Controller
@RequestMapping("/raffle")
public class RaffleController {
	
	@Autowired
	private RaffleService raffleService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/list")
	public String list(Model model, @PageableDefault Pageable pageable, @RequestParam String status) {
		
		model.addAttribute("status", status);
		model.addAttribute("raffleList", raffleService.getRaffleList(pageable, status));
		
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
		
		return "redirect:/raffle/list?status=0";
	}
	
	@GetMapping("/toyroom")
	public String view2(Model model) {
		return "raffle/toyroom";
	}
}

