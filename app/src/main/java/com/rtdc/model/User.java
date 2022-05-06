package com.rtdc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor

@DynamicInsert	//insert 시점에 null이 아닌 컬럼들만 insert
@Setter
public class User {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
	
	@Id
	@Column(unique = true)
	private String username; // 아이디
	
	private String password;
	
	private String nickname;
	
	@Column(columnDefinition = "varchar(5) default 'USER'")
	private String role;
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
}

