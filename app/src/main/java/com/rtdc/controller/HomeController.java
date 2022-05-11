package com.rtdc.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rtdc.model.Post;
import com.rtdc.model.User;
import com.rtdc.service.PostService;
import com.rtdc.service.UserService;

@Controller
public class HomeController{
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	private String getFp(String url, String href) throws IOException {
		Connection connect = Jsoup.connect(url);
		connect.header("User-Agent", "Chrome/101.0.4951.54");
		
		Document document = connect.get();
		Elements elements = document.getElementsByAttributeValue("href", href);
		String fp = elements.text().toString();
		fp = fp.replaceAll("[^0-9]", "");
		return fp;
	}
	

    @GetMapping("/")
    public String index(Model model){
    	
    	List<Post> popularPostList = postService.getPopularPostList();
    	List<Post> newPostList = postService.getNewPostList();
		
		model.addAttribute("popularPostList", popularPostList);
		model.addAttribute("newPostList", newPostList);
		
        return "index";
    }
    
    @PostMapping("/getUser")
    @ResponseBody
    public User getUserInfo(@RequestParam HashMap<String, Object> param) {
    	User user = userService.getUser((String) param.get("username"));
    	if(user != null) {
    		user.setPassword(null);
    	}
    	return user;
    }
    
    @PostMapping("/getOpenseaFp")
    @ResponseBody
    public String getOpenseaFp(@RequestParam HashMap<String, Object> param) throws IOException {
    	
    	String url = (String) param.get("url");
    	String href = (String) param.get("href");
    	
    	return getFp(url, href);
    }
}