package com.rtdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rtdc.model.Board;
import com.rtdc.model.Post;
import com.rtdc.service.BoardService;
import com.rtdc.service.PostService;

@Controller
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private PostService postService;
	
	/**
	 * 게시글 목록 조회
	 * @param model
	 * @param boardId
	 * @param pageable
	 * @param searchText
	 * @return
	 */
	@GetMapping("/list")
	public String list(Model model, @RequestParam Long boardId, @PageableDefault Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		Board board = boardService.getBoard(boardId);
		Page<Post> postList = postService.getPostList(pageable, board, searchText);
		
		model.addAttribute("boardId", boardId);
		model.addAttribute("postList", postList);
		return "event/list";
	}
}
