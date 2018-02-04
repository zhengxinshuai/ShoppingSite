package com.dfbb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dfbb.entity.Areas;
import com.dfbb.entity.City;
import com.dfbb.server.AddressServer;
import com.dfbb.serverImpl.AddressServerImpl;



public class AddressServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String opr= req.getParameter("opr");
		StringBuffer sb=new StringBuffer();
		AddressServer addressServer=new AddressServerImpl();
		if(opr.equals("p")){
			String pIdStr= req.getParameter("pId");
			
			if(pIdStr==null||pIdStr==""){
				return;
			}
			int pId= Integer.parseInt(pIdStr);
			
			//得到当前省下的所有城市
			List<City> cities= addressServer.getCitiesByPId(pId);
			sb.append("[");
			for (int i = 0; i < cities.size(); i++) {
				City city= cities.get(i);
				sb.append("{\"id\":"+city.getId()+",\"cityid\":"+city.getCityid()+",\"city\":"+"\""+city.getCity()+"\",\"provinceId\":"+city.getProvinceId()+"}");
				if (i<cities.size()-1) {
					sb.append(",");
				}
			}
			sb.append("]");
		}else if (opr.equals("city")) {
			String cIdStr= req.getParameter("cId");
			
			if(cIdStr==null||cIdStr==""){
				return;
			}
			int cId= Integer.parseInt(cIdStr);
			List<Areas> areas= addressServer.getAreasByCId(cId);
			sb.append("[");
			for (int i = 0; i < areas.size(); i++) {
				Areas item= areas.get(i);
				sb.append("{\"id\":"+item.getId()+",\"areaid\":"+item.getAreaid()+",\"area\":"+"\""+item.getArea()+"\",\"cityid\":"+item.getCityid()+"}");
				if (i<areas.size()-1) {
					sb.append(",");
				}
			}
			sb.append("]");
			
		}
		
		resp.setContentType("application/json;charset=utf-8");  
		PrintWriter out= resp.getWriter();
		out.write(sb.toString());
		out.flush();
		out.close();
		
	}
	
}
