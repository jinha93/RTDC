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
		
		String[] ipArray = {"121.184.137.181"};
		for(String ip : ipArray) {
			if(regIp.equals(ip)) {
				return false;
			}
		}
		return true;
	}


}
