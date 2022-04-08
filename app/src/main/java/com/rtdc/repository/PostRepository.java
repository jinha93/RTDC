package com.rtdc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rtdc.model.Board;
import com.rtdc.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	List<Post> findByTitle(String title);
	List<Post> findByTitleOrContent(String title, String content);
	
	Page<Post> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
