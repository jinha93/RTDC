package com.rtdc.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtdc.model.Comment;
import com.rtdc.model.Post;
import com.rtdc.repository.CommentRepository;


@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	public List<Comment> getCommentList(Post post){
		return commentRepository.findByPost(post);
	}
	
	public void save(@Valid Comment comment) {
		commentRepository.save(comment);
	}
	
}
