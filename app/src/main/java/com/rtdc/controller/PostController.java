package com.rtdc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.rtdc.model.Post;
import com.rtdc.service.BoardService;
import com.rtdc.service.PostService;
import com.rtdc.validator.PostValidator;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostValidator postValidator;
	
	private PostService postService;
	private BoardService boardService;
	
	public PostController(PostService postService, BoardService boardService) {
		this.postService = postService;
		this.boardService = boardService;
	}
	
	
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam Long boardId, @PageableDefault Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		Board board = boardService.getBoard(boardId);
		Page<Post> postList = postService.getPostList(pageable, board, searchText);
		
		
		model.addAttribute("boardId", boardId);
		model.addAttribute("postList", postList);
		return "post/list";
	}
	
	@GetMapping("/form")
	public String form(Model model, @RequestParam Long boardId, @RequestParam(required = false) Long postId) {
		
		Board board = boardService.getBoard(boardId);
		
		if(postId == null) {
			Post post = new Post();
			post.setBoard(board);
			model.addAttribute("post", post);
		}else {
			model.addAttribute("post", postService.getPost(board, postId));
		}
		return "post/form";
	}
	
	@PostMapping("/form")
	public String form(@Valid Post post, BindingResult bindingResult) {
		postValidator.validate(post, bindingResult);
		if(bindingResult.hasErrors()) {
			return "post/form";
		}
		
		postService.save(post);
		return "redirect:/post/list?boardId=" + post.getBoard().getBoardId();
	}
}
