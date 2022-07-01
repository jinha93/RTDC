package com.rtdc.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Raffle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long raffleId;
	
	@Column(length = 200)
	private String title;
	
	@Column(length = 500)
	private String cardImgPath;
	
	@Column(length = 15000)
	private String content;
	
	private LocalDateTime startDateTime;
	
	private LocalDateTime endDateTime;
	
	private String status;
	
	@ManyToOne
    @JoinColumn(name = "username", nullable = false)
	private User user;
}
