package com.rtdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Board")
public class Board {
	
	@Id
	@Column(nullable = false)
	private long boardId;
	
	@Column(columnDefinition = "VARCHAR(20)", nullable = false)
	private String BoardNm;
}
