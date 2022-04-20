package com.rtdc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtdc.model.Comment;
import com.rtdc.model.Post;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	List<Comment> findByPost(Post post);

}
