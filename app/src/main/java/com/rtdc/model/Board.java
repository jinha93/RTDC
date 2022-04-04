package com.rtdc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length=500, nullable=false)
	private String title;
	
	@Column(length=4000, nullable=false)
	private String content;
	
	@Column(nullable=false, columnDefinition = "date default current_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
    @ColumnDefault("0") //default 0
	private Integer readCnt;
}
