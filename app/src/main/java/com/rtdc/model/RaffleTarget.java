package com.rtdc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class RaffleTarget implements Serializable{
	
	@Id
	@ManyToOne
    @JoinColumn(name = "raffleId", nullable = false)
    public Raffle raffle;
	
	@Id
	@ManyToOne
    @JoinColumn(name = "username", nullable = false)
    public User user;
	
	@Column(length = 100, nullable = false)
	private String twitterHandle;
	
	@Column(length = 100, nullable = false)
	private String discordHandle;
}
