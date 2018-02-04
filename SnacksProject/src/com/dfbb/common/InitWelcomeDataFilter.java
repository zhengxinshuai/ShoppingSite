package com.dfbb.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dfbb.entity.Page;
import com.dfbb.entity.Snack;
import com.dfbb.server.SnackServer;
import com.dfbb.serverImpl.SnackServerImpl;

/**
 * 初始化欢迎页面的数据
 * @author apple
 *
 */
public class InitWelcomeDataFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		SnackServer snackServer=new SnackServerImpl();
		Page page=new Page();
		page.setPerPageCount(8);
		page.setCurrentPageNo(1);
		//得到零食的集合。(卖的最好的8个)
		List<Snack> snacks= snackServer.querySnack(page, OrderByWhat.SellQuntityDesc, "");
		Page page2=new Page();
		page2.setPerPageCount(4);
		page2.setCurrentPageNo(1);
		//得到最新上架的四个零食
		List<Snack> newestSnacks= snackServer.querySnack(page2, OrderByWhat.DateDesc, "");
		
		
		HttpSession session= req.getSession();
		session.setAttribute("snacks", snacks);
		session.setAttribute("newestSnacks", newestSnacks);
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
