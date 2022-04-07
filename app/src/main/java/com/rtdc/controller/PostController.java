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

import com.rtdc.model.Post;
import com.rtdc.service.PostService;
import com.rtdc.validator.PostValidator;

@Controller
@RequestMapping("/post")
public class PostController {
	
	private PostService postService;
	
	@Autowired
	private PostValidator postValidator;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam int boardId, @PageableDefault Pageable pageable, 
			@RequestParam(required = false, defaultValue = "") String searchText) {
		model.addAttribute("boardId", boardId);
		model.addAttribute("postList", postService.getPostList(pageable, searchText));
		return "post/list";
	}
	
	@GetMapping("/form")
	public String form(Model model, @RequestParam(required = false) Long id) {
		if(id == null) {
			model.addAttribute("post", new Post());
		}else {
//			model.addAttribute("post", postService.getPost(id));
		}
		return "post/form";
	}
	
	@PostMapping("/form")
	public String form(@Valid Post post, BindingResult bindingResult) {
		postValidator.validate(post, bindingResult);
		if(bindingResult.hasErrors()) {
			return "post/form";
		}
		return "redirect:/post/list";
	}
}
