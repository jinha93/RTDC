package com.rtdc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rtdc.model.Board;
import com.rtdc.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	Page<Post> findByBoard(Board board, Pageable pageable);
	
	Page<Post> findByBoardAndTitleContainingOrContentContaining(Board board, String title, String content, Pageable pageable);
	
	Post findByBoardAndPostId(Board board, Long postId);

}
