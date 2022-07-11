package com.rtdc.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@DynamicInsert	//insert 시점에 null이 아닌 컬럼들만 insert
@IdClass(HolderId.class)
public class Holder {
	
	@Id
	private String gb;
	
	@Id
	@ManyToOne
    @JoinColumn(name = "username", nullable = false)
    public User user;
	
	@Id
	private String tokenId;
	
	@Column(columnDefinition = "varchar(1) default 'N'")
	private String delYn;
}
