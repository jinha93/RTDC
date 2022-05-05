package com.rtdc.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rtdc.model.Board;
import com.rtdc.model.Post;
import com.rtdc.repository.PostRepository;


@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public List<Post> getPopularPostList(){
		return postRepository.findTop8ByOrderByReadCntDesc();
	}
	
	public List<Post> getNewPostList(){
		return postRepository.findTop8ByOrderByRegDateTimeDesc();
	}
	
	public Page<Post> getPostList(Pageable pageable, Board board, String searchText){
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); //page는 0부터 시작
		pageable = PageRequest.of(page, 15, Sort.by("post_id").descending());
		
		Post post = new Post();
		post.setTitle(searchText);
		post.setContent(searchText);
		post.setBoard(board);
		
		return postRepository.findByBoardAndTitleContainingOrContentContaining(post, pageable);
	}

	
	public Post getPost(Board board, Long postId) {
		return postRepository.findByBoardAndPostId(board,postId);
	}

	public void save(@Valid Post post) {
		postRepository.save(post);
	}
	
}
