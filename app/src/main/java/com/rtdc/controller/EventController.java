package com.rtdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rtdc.service.EventService;

@Controller
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	/**
	 * 게시글 목록 조회
	 * @param model
	 * @param boardId
	 * @param pageable
	 * @param searchText
	 * @return
	 */
	@GetMapping("/list")
	public String list(Model model,  @RequestParam Long boardId, @PageableDefault Pageable pageable) {
		
		model.addAttribute("boardId", boardId);
		model.addAttribute("eventList", eventService.getEventList(pageable));
		
		return "event/list";
	}
}
