package com.rtdc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rtdc.model.Comment;
import com.rtdc.model.User;
import com.rtdc.service.CommentService;
import com.rtdc.service.UserService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/form")
	public String form(@Valid Comment comment) {
		
		Long postId = comment.getPost().getPostId();
		Long boardId = comment.getPost().getBoard().getBoardId();
		
		//현재 로그인한 사용자정보
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		User user = userService.getUser(userName);
		comment.setUser(user);
		
		commentService.save(comment);
		
		//댓글 작성 시 +5p
		userService.plusPoint(user, 5);
		
		return "redirect:/post/view?boardId=" + boardId + "&postId=" + postId;
	}
}
