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
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class PointHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@PositiveOrZero
	@NotNull
	private int point;
	
	private String content;
	
	@Column
	@CreationTimestamp
	private LocalDateTime dateTime;
	
	@ManyToOne
    @JoinColumn(name = "username", nullable = false)
    public User user;
}
