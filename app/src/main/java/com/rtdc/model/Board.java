package com.rtdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Board {
	
	@Id
	@Column(nullable = false)
	private long boardId;
	
	@Column(columnDefinition = "VARCHAR(20)", nullable = false)
	private String boardNm;
	
//	@OneToMany(mappedBy = "board")
//    private List<Post> posts = new ArrayList<>();
}
