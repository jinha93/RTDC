package com.rtdc.service;

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
	
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

	public void signup(User user) {
		user.encodePassword(passwordEncoder);
		user.setRole("USER");
		userRepository.save(user);
	}
	
	/**
	 * 포인트 +
	 * @param user
	 * @param point
	 * @return
	 */
	public User plusPoint(User user, int point) {
		user.setPoint(user.getPoint()+point);
		return userRepository.save(user); 
	}
	
	/**
	 * 포인트 -
	 * @param user
	 * @param point
	 * @return
	 */
	public User minusPoint(User user, int point) {
		user.setPoint(user.getPoint()-point);
		return userRepository.save(user);
	}
	
	public User saveLoginIp(User user, String ip) {
		user.setLastLoginIp(ip);
		return userRepository.save(user);
	}

}
