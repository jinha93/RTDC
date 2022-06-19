package com.rtdc.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

public class ToyRoom {
	
	@Id
	private String id;
	
	private String backgroundColor;
	
	@ManyToOne
    @JoinColumn(name = "username", nullable = false)
	private User user;
}
