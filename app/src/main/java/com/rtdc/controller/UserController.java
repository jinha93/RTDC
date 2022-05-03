package com.rtdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.rtdc.model.User;
import com.rtdc.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/{role}/{username}/{password}")
	public User createUser(@ModelAttribute User user) {
		return userService.createUser(user);
	}
	
}
