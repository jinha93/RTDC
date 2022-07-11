package com.rtdc.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class HolderId implements Serializable{
	
	private String gb;
	private String user;
	private String tokenId;
	
}
