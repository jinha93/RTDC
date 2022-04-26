package com.rtdc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rtdc.model.Comment;
import com.rtdc.model.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	List<Comment> findByPost(Post post);

}
