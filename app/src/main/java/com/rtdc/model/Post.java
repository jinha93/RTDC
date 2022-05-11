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
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor

@DynamicUpdate	//update 시점에 null이 아닌 컬럼들만 update
@DynamicInsert	//insert 시점에 null이 아닌 컬럼들만 insert
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long postId;
	
	@NotNull
	@Size(min=2, max=200, message = "제목은 2자 이상 200자 이하입니다.")
	private String title;
	
	@Column(length = 4000)
	private String content;
	
	@Column
	@CreationTimestamp
	private LocalDateTime regDateTime;
	
    @ColumnDefault("0") //default 0
	private Integer readCnt;
    
    @ColumnDefault("0") //default 0
    private Integer likeCnt;
    
    @Column(columnDefinition = "varchar(1) default 'N'")
    private String delYn;
    
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    public User user;
    
    @ManyToOne
	@JoinColumn(name = "boardId", nullable = true)
	public Board board;
}
