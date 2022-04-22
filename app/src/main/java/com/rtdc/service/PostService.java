package com.rtdc.service;

import javax.validation.Valid;

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
	
//	private final EntityManager em;
	
	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Page<Post> getPostList(Pageable pageable, Board board, String searchText){
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); //page는 0부터 시작
		pageable = PageRequest.of(page, 10, Sort.by("post_id").descending());
		
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
