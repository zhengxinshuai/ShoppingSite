package com.dfbb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.dfbb.common.OrderState;
import com.dfbb.entity.Order;
import com.dfbb.entity.Page;
import com.dfbb.entity.User;
import com.dfbb.server.OrderServer;
import com.dfbb.server.UserServer;
import com.dfbb.serverImpl.OrderServerImpl;
import com.dfbb.serverImpl.UserServerImpl;

public class ManagerServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String opr = req.getParameter("opr");
		HttpSession session= req.getSession();
		if (opr==null) {
			return;
		}
		
		
		if (opr.equals("managerLogin")) {
			
			String path = req.getContextPath();
			String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/";
			
			//管理员登录
			 String mName = req.getParameter("managerName");
			 String pwd2 = req.getParameter("pwd2");
			 pwd2= UserServlet.md5ToUpperCase(pwd2);
			 UserServer userServer=new UserServerImpl();
			 User user=userServer.managerLogin(mName, pwd2);
			 if (user==null) {
				//管理员账号错误
				session.setAttribute("msg", "不是管理员不要乱登哦!");
				resp.setStatus(302);
				resp.setHeader("location", basePath);
			}else {
				OrderServer orderServer=new OrderServerImpl();
				session.setAttribute("manager", user);
				session.setMaxInactiveInterval(60000);//
				//转发到管理员页面。
				resp.setStatus(302);
				resp.setHeader("location", basePath+"Manager/manager.jsp");
			}
			 
			 
			 
		}else if (opr.equals("searchPage")) {
				String searchType= req.getParameter("searchType");
				String perPageCountStr= req.getParameter("perPageCount");
				String currentPageNumStr= req.getParameter("currentPageNum");
				
				int perPageCount= Integer.parseInt(perPageCountStr);
				int currentPageNum= Integer.parseInt(currentPageNumStr);
				OrderState orderState=null;
				//对查找条件进行分类赋值
				switch (searchType) {
			case "WEIFAHUO":
				orderState=OrderState.WEIFAHUO;
				break;
			case "YIFAHUO":
				orderState=OrderState.YIFAHUO;
				break;
			case "YISHOUHUO":
				orderState=OrderState.YISHOUHUO;
				break;
			case "ALL":
				orderState=OrderState.ALL;
				break;
				}
				//获得总条数
				OrderServer orderServer=new OrderServerImpl();
				int totalItemCount= orderServer.getOrdersCount(orderState);
				
				//查找到订单的总条数 （根据条件）
				
				
				Page page=new Page();
				page.setPerPageCount(perPageCount);
				page.setCurrentPageNo(currentPageNum);
				page.setTotalItemCount(totalItemCount);
				
				
				List<Order> orders= orderServer.getOrdersByCondition(page, orderState);
				String ordersJSON= JSON.toJSONStringWithDateFormat(orders, "yyyy-MM-dd HH:mm:ss");
				
				page.setOrderJson(ordersJSON);
				String pageJSON=JSON.toJSONString(page);//得到页面json和订单的json集合
				resp.setContentType("application/json;charset=utf-8");  
				PrintWriter out= resp.getWriter();
				out.write(pageJSON);
				out.flush();
				out.close();
				
		}else if (opr.equals("deleverGood")) {
			//验证是不是管理员
			 User user= (User)session.getAttribute("manager");
			 if (user==null) {
				return;
			}
			 String serinumStr = req.getParameter("serinum");
			 int serinum= Integer.parseInt(serinumStr);
			 OrderServer orderServer=new OrderServerImpl();
			 int res= orderServer.deliverOrder(serinum);
			 String info="";
			 if (res>0) {
				info="发货成功!";
			}else {
				info="发货失败!";
			}
					 
			 resp.setContentType("text/plain;charset=utf-8");
				PrintWriter out = resp.getWriter();
				
				out.print(info);
				out.flush();
				out.close();
			 
			 
		}else if (opr.equals("dropUser")) {
			String userName = req.getParameter("userName");
			if (userName==null) {
				return;
			}
			ServletContext application = req.getSession().getServletContext();
			 HashMap<String, HttpSession> userMap = (HashMap<String, HttpSession>)application.getAttribute("users");
			 if (userMap==null) {
				return;
			}
			 HttpSession session2= userMap.get(userName);
			
			 session2.invalidate();
			 resp.setContentType("text/plain;charset=utf-8");
				PrintWriter out = resp.getWriter();
				
				out.print("踢出成功!");
				out.flush();
				out.close();
			
			
		}
		
	}
	
}
