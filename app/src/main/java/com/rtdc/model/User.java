package com.rtdc.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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

@DynamicUpdate	//update 시점에 null이 아닌 컬럼들만 update
@DynamicInsert	//insert 시점에 null이 아닌 컬럼들만 insert
public class User{
	
	@Id
	@Column(unique = true)
	private String username; // 아이디
	
	private String password;
	
	private String nickname;
	
	@Column(columnDefinition = "varchar(50) default 'USER'", nullable = false)
	private String role;
	
	@Column
	@CreationTimestamp
	private LocalDateTime lastLoginDateTime;
	
	@Column(nullable = true)
	private String lastLoginIp;
	
	@ColumnDefault("0") //default 0
	@PositiveOrZero
	private int point;
	
	@Column(nullable = true)
	private String profileImgPath = "/image/4006";
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

}

