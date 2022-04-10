package com.rtdc.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long postId;
	
	@NotNull
	@Size(min=2, max=200, message = "제목은 2자 이상 200자 이하입니다.")
	private String title;
	
	@Column(length = 4000)
	private String content;
	
	@Column(updatable=false)
	@CreationTimestamp
	private LocalDateTime regDateTime;
	
    @ColumnDefault("0") //default 0
	private Integer readCnt;
    
    @Column(length = 200, nullable = true)
    private String regIp;
    
//    @ManyToOne
//    @JoinColumn(name = "userId", nullable = true)
//    public User user;
//    
    @ManyToOne
	@JoinColumn(name = "boardId", nullable = true)
	public Board board;
}
