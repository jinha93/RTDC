package com.rtdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rtdc.repository.BoardRepository;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardRepository boardRepository;
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("boards",boardRepository.findAll());
		return "board/list";
	}
}
