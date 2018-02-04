package com.dfbb.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dfbb.entity.User;

/**
 * 此过滤器处理在操作所有页面时会话失效的页面，若会话失效，则
 * @author apple
 *
 */
public class UserLoginFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		HttpSession session= req.getSession();
		
		User user=(User) session.getAttribute("user");
		if (user!=null) {//有User对象，则放过此请求。
			chain.doFilter(request, response);
		}else {//如果session中没有user，则转发到主页
			session.setAttribute("loginMsg", "请先登录");
			req.getRequestDispatcher("/login/login.jsp").forward(request, response);
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
