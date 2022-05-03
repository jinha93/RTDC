package com.rtdc.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.rtdc.model.Post;
import com.rtdc.service.BoardService;
import com.rtdc.service.PostService;

@Controller
public class HomeController{
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private PostService postService;

	
    @GetMapping("/")
    public String index(Model model) throws IOException{
    	List<Post> popularPostList = postService.getPopularPostList();
    	List<Post> newPostList = postService.getNewPostList();
		
		model.addAttribute("popularPostList", popularPostList);
		model.addAttribute("newPostList", newPostList);
		
        return "index";
    }
}