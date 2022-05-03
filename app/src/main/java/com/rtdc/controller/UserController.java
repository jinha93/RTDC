package com.rtdc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtdc.model.User;
import com.rtdc.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@GetMapping("/signup")
	public String form(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "user/form";
	}
	
	@PostMapping("/signup")
	public String signUp(@Valid User user, HttpServletRequest request ) {
		userService.signup(user);
		return "redirect:/user/login";
	}
	
}
