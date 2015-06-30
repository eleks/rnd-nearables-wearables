package com.eleks.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.eleks.service.UserService;

@Component
public class MainInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
//		final String accessTokenParameter = request.getParameter("access_token");
//		if(accessTokenParameter != null) {
//			final boolean activated = userService.validateAccessToken(accessTokenParameter);
//			if(!activated) {
//				throw new UnauthorizedException("Access token invalid");
//			}
//		}
		
		return super.preHandle(request, response, handler);
	}
	
}
