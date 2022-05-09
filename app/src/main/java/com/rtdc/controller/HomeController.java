package com.rtdc.controller;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.rtdc.model.Post;
import com.rtdc.service.PostService;

@Controller
public class HomeController{
	
	@Autowired
	private PostService postService;
	
	private final String mtdzUrl = "https://opensea.io/collection/mtdz-1";
	private final String mtdzhref = "/collection/mtdz-1?search[sortAscending]=true&search[sortBy]=PRICE&search[toggles][0]=BUY_NOW";
	private final String mtgUrl = "https://opensea.io/collection/meta-toy-gamers";
	private final String mtghref = "/collection/meta-toy-gamers?search[sortAscending]=true&search[sortBy]=PRICE&search[toggles][0]=BUY_NOW";
	private final String mtbUrl = "https://opensea.io/collection/meta-toy-bricks";
	private final String mtbhref = "/collection/meta-toy-bricks?search[sortAscending]=true&search[sortBy]=PRICE&search[toggles][0]=BUY_NOW";
	
	
	
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
    public String index(Model model) throws IOException{
    	
    	model.addAttribute("mtdzFp",getFp(mtdzUrl, mtdzhref));
    	model.addAttribute("mtgFp",getFp(mtgUrl, mtghref));
    	model.addAttribute("mtbFp",getFp(mtbUrl, mtbhref));
    	
    	List<Post> popularPostList = postService.getPopularPostList();
    	List<Post> newPostList = postService.getNewPostList();
		
		model.addAttribute("popularPostList", popularPostList);
		model.addAttribute("newPostList", newPostList);
		
        return "index";
    }
}