package com.rtdc.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rtdc.model.ToyRoom;
import com.rtdc.model.User;
import com.rtdc.service.ToyRoomService;
import com.rtdc.service.UserService;

@Controller
@RequestMapping("/toyRoom")
public class ToyRoomController {
	
	@Autowired
	private ToyRoomService toyRoomService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/view")
	public String view(Model model) {
		
		//현재 로그인한 사용자정보
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		User user = userService.getUser(userName);
		
		model.addAttribute("user", user);
		model.addAttribute("toyRoomList",toyRoomService.getToyRoomList());
		return "toyRoom/toyroom";
	}
	
	@PostMapping("/save")
	@ResponseBody
	public String save(@RequestBody List<HashMap<String,Object>> paramList) {
		//현재 로그인한 사용자정보
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		User user = userService.getUser(userName);
		
		for(HashMap<String,Object> map : paramList) {
			ToyRoom toyRoom = new ToyRoom();
			toyRoom.setId((String)map.get("id"));
			toyRoom.setBackgroundColor((String)map.get("backgroundColor"));
			toyRoom.setUser(user);
			
			toyRoomService.save(toyRoom);
		}
		
		
		return "test";
	}
	
}

