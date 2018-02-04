package com.dfbb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dfbb.server.CollectionServer;
import com.dfbb.serverImpl.CollectionServerImpl;

public class CollectionServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String opr = req.getParameter("opr");
		if (opr.equals("addCollection")) {
			String sidStr = req.getParameter("sid");
			String uidStr = req.getParameter("uid");
			int sid= Integer.parseInt(sidStr);
			int uid=Integer.parseInt(uidStr);
			CollectionServer collectionServer=new CollectionServerImpl();
			int res= collectionServer.addCollection(uid, sid);
			String info="";
			if (res>0) {
				info="收藏成功!";
			}else {
				info="收藏失败";
			}
			resp.setContentType("text/plain;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(info);
			out.flush();
			out.close();
			
		}
	}
	
}
