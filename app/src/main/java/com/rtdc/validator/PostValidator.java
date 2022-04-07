package com.rtdc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

import com.rtdc.model.Post;

@Component
public class PostValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Post.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Post b = (Post) target;
		if(StringUtils.isEmpty(b.getContent())) {
			errors.rejectValue("content", "key", "내용을 입력하세요.");
		}
	}

}
