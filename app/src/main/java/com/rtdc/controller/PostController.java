package com.rtdc.controller;

import javax.servlet.http.HttpServletRequest;
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

import com.rtdc.Common;
import com.rtdc.model.Board;
import com.rtdc.model.Comment;
import com.rtdc.model.Post;
import com.rtdc.service.BoardService;
import com.rtdc.service.CommentService;
import com.rtdc.service.PostService;
import com.rtdc.validator.PostValidator;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostValidator postValidator;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CommentService commentService;
	
	
	/**
	 * 게시글 목록 조회
	 * @param model
	 * @param boardId
	 * @param pageable
	 * @param searchText
	 * @return
	 */
	@GetMapping("/list")
	public String list(Model model, @RequestParam Long boardId, @PageableDefault Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		Board board = boardService.getBoard(boardId);
		Page<Post> postList = postService.getPostList(pageable, board, searchText);
		
		model.addAttribute("boardId", boardId);
		model.addAttribute("postList", postList);
		return "post/list";
	}
	
	/**
	 * 게시글 조회
	 * @param model
	 * @param boardId
	 * @param postId
	 * @return
	 */
	@GetMapping("/view")
	public String view(Model model, @RequestParam Long boardId, @RequestParam(required = false) Long postId) {
		Board board = boardService.getBoard(boardId);
		
		//게시글 조회
		Post post = postService.getPost(board, postId);
		//조회수 증가
		post.setReadCnt(post.getReadCnt()+1);
		postService.save(post);
		
		model.addAttribute("post", post);
		
		Comment comment = new Comment();
		comment.setPost(post);
		model.addAttribute("comment", comment);
		
		model.addAttribute("commentList", commentService.getCommentList(post));
		
		return "post/view";
	}
	
	/**
	 * 게시글 수정 페이지 진입
	 * @param model
	 * @param boardId
	 * @param postId
	 * @return
	 */
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
	
	/**
	 * 게시글 수정 저장
	 * @param post
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@PostMapping("/form")
	public String form(@Valid Post post, BindingResult bindingResult, HttpServletRequest request) {
		
		//validate
		postValidator.validate(post, bindingResult);
		if(bindingResult.hasErrors()) {
			return "post/form";
		}
		
		//getIp
		Common common = new Common();
		String regIp = common.getIp(request);
		post.setRegIp(regIp);
		
		
		postService.save(post);
		return "redirect:/post/list?boardId=" + post.getBoard().getBoardId();
	}
}
