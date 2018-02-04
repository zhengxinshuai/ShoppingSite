package com.dfbb.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dfbb.entity.User;
import com.dfbb.server.UserServer;
import com.dfbb.serverImpl.UserServerImpl;

/**
 * 当请求登录界面时，查看cookie，cookie比对的上就直接登录主页
 * @author apple
 *
 */
public class RememberAccountFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		
		HttpServletRequest req=(HttpServletRequest)arg0;
		HttpServletResponse resp=(HttpServletResponse)arg1;
		
		
		HttpSession session= req.getSession();
		Cookie[] cookies= req.getCookies();
		//查找cookie中的
		if (cookies != null) {
   			if (cookies.length >= 2) {
   				String valueName = null;
   				String valuePwd = null;
   				for (Cookie c : cookies) {
   					String name = c.getName();
   					if (name.equals("uname")) {
   						valueName = c.getValue();
   						
   					}
   					if (name.equals("pwd")) {
   						valuePwd = c.getValue();
   						
   					}
   				}
   				User user=new User(valueName, valuePwd);
   				UserServer userServer=new UserServerImpl();
   				User findUser = userServer.userLogin(user);
   				if (findUser!=null) {
   					session.setAttribute("user", user);
   					req.getRequestDispatcher("/onOprator/mainPage/welcome.jsp").forward(req, resp);
				}
   			}else {
   				arg2.doFilter(arg0, arg1);
			}
   		}else {
			arg2.doFilter(arg0, arg1);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
