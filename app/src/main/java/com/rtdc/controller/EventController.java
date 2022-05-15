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

import com.rtdc.model.Event;
import com.rtdc.model.User;
import com.rtdc.service.EventService;
import com.rtdc.service.UserService;

@Controller
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 게시글 목록 조회
	 * @param model
	 * @param boardId
	 * @param pageable
	 * @param searchText
	 * @return
	 */
	@GetMapping("/list")
	public String list(Model model, @PageableDefault Pageable pageable, @RequestParam String status) {
		
		model.addAttribute("status", status);
		model.addAttribute("eventList", eventService.getEventList(pageable, status));
		
		return "event/list";
	}
	
	@GetMapping("/form")
	public String form(Model model, @RequestParam(required = false) Long eventId) {
		
		if(eventId == null) {
			Event event = new Event();
			model.addAttribute("event", event);
		}else {
			model.addAttribute("event", eventService.getEvent(eventId));
		}
		return "event/form";
	}
	
	@PostMapping("/form")
	public String form(@Valid Event event, String startDateVal, String endDateVal) {
		
		//현재 로그인한 사용자정보
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		User user = userService.getUser(userName);
		event.setUser(user);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		startDateVal = startDateVal.replaceAll("-", "") + "000000";
		endDateVal = endDateVal.replaceAll("-", "") + "000000";
		
		event.setStartDateTime(LocalDateTime.parse(startDateVal, dtf));
		event.setEndDateTime(LocalDateTime.parse(endDateVal, dtf));
		
		event.setStatus("0");
		
		eventService.save(event);
		
		return "redirect:/event/list?status=0";
	}
}
