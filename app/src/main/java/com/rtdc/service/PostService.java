package com.rtdc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rtdc.model.Post;
import com.rtdc.repository.PostRepository;


@Service
public class PostService {
	
	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Page<Post> getPostList(Pageable pageable, String searchText){
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); //page는 0부터 시작
		pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		
		return postRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
	}
	
//	public Post getPost(Long postId) {
//		return postRepository.findById(postId).orElse(null);
//	}
	
	public Post save(Post post) {
		return postRepository.save(post);
	}
	
}
