package com.rtdc.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentId;
	
	private long parentCommentId;
	
	private String randomName;
	
	private String content;
	
	@Column(updatable=false)
	@CreationTimestamp
	private LocalDateTime regDateTime;
	
	private String regIp;
	
	@ManyToOne
	@JoinColumn(name = "postId", nullable = false)
	public Post post;
}
