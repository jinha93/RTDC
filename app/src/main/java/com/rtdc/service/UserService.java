package com.rtdc.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rtdc.model.User;
import com.rtdc.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRole())
				.build();
	}
	
	/**
	 * 유저정보조회
	 * @param username
	 * @return
	 */
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

	/**
	 * 회원가입
	 * @param user
	 * @return
	 */
	public User signup(User user) {
		user.encodePassword(passwordEncoder);
		user.setRole("USER");
		return userRepository.save(user);
	}
	
	/**
	 * 포인트 저장
	 * @param user
	 * @param point
	 * @return
	 */
	public User savePoint(User user, int point) {
		//포인트 저장
		user.setPoint(point);
		return userRepository.save(user); 
	}
	
	/**
	 * 로그인정보저장
	 * @param user
	 * @param ip
	 * @param curDateTime
	 * @return
	 */
	public User saveLoginInfo(User user, String ip, LocalDateTime curDateTime) {
		user.setLastLoginIp(ip);
		user.setLastLoginDateTime(curDateTime);
		return userRepository.save(user);
	}

}
