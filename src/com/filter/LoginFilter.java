package com.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.util.SystemUtil;

@Component
public class LoginFilter implements Filter{
	@Value("${userName}")
	public String userName;
	@Override
	public void destroy() {
		
	}
	private Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		servletResponse.setContentType("text/javascript charset=UTF-8");
		servletResponse.setCharacterEncoding("UTF-8");
		servletResponse.setHeader("Access-Control-Allow-Origin", "*");
		
		try {
			chain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			logger.error("system error :{}",e);
			servletResponse.getWriter().print(SystemUtil.request(0, "", "系统错误，请联系管理员").toString());
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
