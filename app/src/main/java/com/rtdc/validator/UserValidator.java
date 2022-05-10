package com.rtdc.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

import com.rtdc.model.Post;
import com.rtdc.model.User;
import com.rtdc.repository.UserRepository;

@Component
public class UserValidator implements Validator{
	
	@Autowired
	UserRepository userRepositoryr;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User b = (User) target;
		//ID 중복확인
		if(userRepositoryr.existsByUsername(b.getUsername())) {
			errors.rejectValue("username", "key", "이미 사용중인 아이디 입니다.");
		}
		
		//비밀번호
		Pattern p1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
		Matcher m1 = p1.matcher(b.getPassword());
		if(!m1.find()) {
			errors.rejectValue("password", "key", "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.");
		}
		
		//닉네임 중복확인
		if(userRepositoryr.existsByNickname(b.getNickname())) {
			errors.rejectValue("nickname", "key", "이미 사용중인 닉네임 입니다.");
		}
	}

}
