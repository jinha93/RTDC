package com.rtdc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rtdc.model.Board;
import com.rtdc.service.BoardService;
import com.rtdc.validator.BoardValidator;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private BoardService boardService;
	
	@Autowired
	private BoardValidator boardValidator;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@RequestMapping("/list")
	public String list(@PageableDefault Pageable pageable, Model model) {
		model.addAttribute("boardList", boardService.getBoardList(pageable));
		return "board/list";
	}
	
	@GetMapping("/form")
	public String form(Model model, @RequestParam(required = false) Long id) {
		if(id == null) {
			model.addAttribute("board", new Board());
		}else {
			model.addAttribute("board", boardService.getBoard(id));
		}
		return "board/form";
	}
	
	@PostMapping("/form")
	public String form(@Valid Board board, BindingResult bindingResult) {
		boardValidator.validate(board, bindingResult);
		if(bindingResult.hasErrors()) {
			return "board/form";
		}
		Long id = boardService.save(board).getId();
		return "redirect:/board/list";
//		return "redirect:/board/form?id=" + id;
	}
}
