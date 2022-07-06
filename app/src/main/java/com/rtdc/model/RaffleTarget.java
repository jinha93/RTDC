package com.rtdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Data
@DynamicInsert	//insert 시점에 null이 아닌 컬럼들만 insert
@IdClass(RaffleTargetId.class)
public class RaffleTarget {
	
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
	
	@Column(columnDefinition = "varchar(1) default 'N'")
	private String winnerYn;
}
