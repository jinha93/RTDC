package com.rtdc.common;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.rtdc.model.User;
import com.rtdc.service.PointHistoryService;
import com.rtdc.service.UserService;

@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private PointHistoryService pointHistoryService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// IP, 세션 ID
		WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
		System.out.println("IP : " + web.getRemoteAddress());
		System.out.println("Session ID : " + web.getSessionId());
		
		// 인증 ID
		System.out.println("name : " + authentication.getName());
		
		// 권한 리스트
		System.out.println("권한 : " + authentication.getAuthorities());
		
		//유저정보 조회
		User user = userService.getUser(authentication.getName());
		
		//오늘날짜가 유저의 마지막 접속일자보다 클 경우 포인트 적립(출석체크)
		LocalDateTime curDateTime = LocalDateTime.now();
		if(curDateTime.toLocalDate().isAfter(user.getLastLoginDateTime().toLocalDate())) {
			
			//출석체크 시 +100p
			int point = pointHistoryService.savePoint(user, 100, curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 출석체크");
			userService.savePoint(user, point);
			userService.savePoint(user, 100);
		}
		
		//마지막접속 IP, 마지막접속 일시 저장
		String ip = web.getRemoteAddress();
		userService.saveLoginInfo(user, ip, curDateTime);
		
		response.sendRedirect("/");
	}

}
