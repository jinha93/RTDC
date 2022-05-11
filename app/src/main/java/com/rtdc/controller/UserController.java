
package com.rtdc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rtdc.model.User;
import com.rtdc.service.UserService;
import com.rtdc.validator.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(Model model) {
		return "user/login";
	}
	
	@GetMapping("/signup")
	public String form(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "user/form";
	}
	
	@PostMapping("/signup")
	public String signUp(@Valid User user, BindingResult bindingResult) {
		
		//validate
		userValidator.validate(user, bindingResult);
		if(bindingResult.hasErrors()) {
			return "user/form";
		}
		
		//회원등록
		userService.signup(user);
		
		return "redirect:/user/login";
	}
	
}
