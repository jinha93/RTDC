package com.rtdc.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RaffleTargetId implements Serializable{
	
	private long raffle;
	private String user;
	
}
