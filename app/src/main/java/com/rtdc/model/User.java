package com.rtdc.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor

@DynamicInsert	//insert 시점에 null이 아닌 컬럼들만 insert
@DynamicUpdate	//update 시점에 null이 아닌 컬럼들만 update
public class User{
	
	@Id
	@Column(unique = true)
	private String username; // 아이디
	
	private String password;
	
	private String nickname;
	
	@Column(columnDefinition = "varchar(5) default 'USER'", nullable = false)
	private String role;
	
	@Column
	@CreationTimestamp
//	@UpdateTimestamp
	private LocalDateTime lastLoginDateTime;
	
	@ColumnDefault("0") //default 0
	@PositiveOrZero
	private int point;
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

}

