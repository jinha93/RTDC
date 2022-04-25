package com.rtdc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.rtdc.Common;


public class IpBanInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//getIp
		Common common = new Common();
		String regIp = common.getIp(request);
		
		//218.55.83.131 --한야형
		String[] ipArray = {""};
		for(String ip : ipArray) {
			if(regIp.equals(ip)) {
				return false;
			}
		}
		return true;
	}


}
