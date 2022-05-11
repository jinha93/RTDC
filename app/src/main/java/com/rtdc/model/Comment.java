package com.rtdc.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentId;
	
	private long parentCommentId;
	
	@Size(max = 300, message = "300자 이상 입력할 수 없습니다.")
	private String content;
	
	@Column(updatable=false)
	@CreationTimestamp
	private LocalDateTime regDateTime;
	
	private String regIp;
	
	@ManyToOne
	@JoinColumn(name = "postId", nullable = false)
	public Post post;
	
	@ManyToOne
    @JoinColumn(name = "username", nullable = false)
    public User user;
}
