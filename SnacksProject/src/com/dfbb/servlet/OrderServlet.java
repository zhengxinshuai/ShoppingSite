package com.dfbb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.dfbb.common.CommentState;
import com.dfbb.common.OrderState;
import com.dfbb.dao.UserDao;
import com.dfbb.daoImpl.UserDaoImpl;
import com.dfbb.entity.Address;
import com.dfbb.entity.CartItem;
import com.dfbb.entity.Order;
import com.dfbb.entity.Snack;
import com.dfbb.entity.SnackComment;
import com.dfbb.entity.User;
import com.dfbb.server.AddressServer;
import com.dfbb.server.OrderServer;
import com.dfbb.server.SnackServer;
import com.dfbb.server.UserServer;
import com.dfbb.serverImpl.AddressServerImpl;
import com.dfbb.serverImpl.OrderServerImpl;
import com.dfbb.serverImpl.SnackServerImpl;
import com.dfbb.serverImpl.UserServerImpl;
import com.dfbb.tool.ArithUtil;
/**
 * 关于订单的Servlet
 * @author apple
 *
 */
public class OrderServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String opr = req.getParameter("opr");
		
		HttpSession session= req.getSession();
		String path = req.getContextPath();
		String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/";
		
		if (opr==null) {
			opr="enterBuyPage";
		}
		
		
		if (opr.equals("enterBuyPage")) {//当操作为进入付款页面的时候
			String uidStr= req.getParameter("uid");
			
			String data= req.getParameter("data");
			HashMap<String, String> datasMap= (HashMap<String, String>)session.getAttribute("datas");
			if ((datasMap==null)) {
				datasMap=new HashMap<String, String>();
				if (data!=null) {
					datasMap.put("uid", uidStr);
				datasMap.put("data", data);
					session.setAttribute("datas", datasMap);
				}
				
			}	
			if (datasMap.get("uid")==null) {
				return;
			}
			if (uidStr==null) {
				uidStr=datasMap.get("uid");
				data=datasMap.get("data");
			}
			
			if (!datasMap.get("uid").equals(uidStr)) {
				datasMap.put("uid", uidStr);
				datasMap.put("data", data);
				session.setAttribute("datas", datasMap);
			}
			if (uidStr==null) {
				return;
			}
			//和session中的uid做判断，不匹配则return
			int uid= Integer.parseInt(uidStr);
			User sUser=  (User)session.getAttribute("user");
			if (sUser!=null&&sUser.getId()!=uid) {
				return;
			}
			
			List<CartItem> cartItems= JSON.parseArray(data, CartItem.class);
			SnackServer snackServer=new SnackServerImpl();
			for (int i = 0; i < cartItems.size(); i++) {
				CartItem cartItem= cartItems.get(i);
				int sid= cartItem.getSid();
				int quantity= cartItem.getQuantity();
				double singlePrice= snackServer.getSinglePriceBySid(sid);
				Snack snack= snackServer.getSnackBySid(sid);
				cartItem.setSinglePrice(singlePrice);
				double total= ArithUtil.mul(singlePrice, quantity);
				cartItem.setItemTotalPrice(total);
				cartItem.setSnack(snack);
			}
			
			
			
			//找到这个uid下所有地址的集合
			AddressServer addressServer=new AddressServerImpl();
			List<Address> addresses= addressServer.getAddressesByUid(uid);
			
			//Mark 接下来把这个集合放到作用域中，转发到购买页面
			session.setAttribute("cartItems", cartItems);
			session.setAttribute("addresses", addresses);
	
			req.getRequestDispatcher("/onOprator/pay/pay.jsp").forward(req, resp);
			
			
			
		}else if (opr.equals("addAddress")) {
			
			String provinceStr= req.getParameter("province");
			String cityStr= req.getParameter("city");
			String areaStr= req.getParameter("area");
			String street= req.getParameter("street");
			String contactPerson=req.getParameter("person");
			String phoneNumStr=req.getParameter("phonenum");
			String uidStr=req.getParameter("uid");
			
			int provinceId= Integer.parseInt(provinceStr);
			int cityId= Integer.parseInt(cityStr);
			int areaId= Integer.parseInt(areaStr);
			int uid= Integer.parseInt(uidStr);
			Address address=new Address();
			address.setAreaid(areaId);
			address.setCityid(cityId);
			address.setContactPerson(contactPerson);
			address.setPhoneNum(phoneNumStr);
			address.setProvinceid(provinceId);
			address.setStreet(street);
			address.setUserId(uid);
			address.setCountry("中国");
			AddressServer addressServer=new AddressServerImpl();
			int res= addressServer.addAddress(address);
			//返回添加的地址id  有用
			
			resp.setContentType("text/plain;charset=utf-8");
			PrintWriter out = resp.getWriter();
			String info="";
			if (res<0) {
				info="添加失败!";
			}else {
				int addressid=addressServer.getTheUserMaxAddressId(uid);
				
				info=addressid+"";
			}
			out.print(info);
			out.flush();
			out.close();
			
			
		}else if (opr.equals("deleteAddress")) {
			String addressIdStr = req.getParameter("addressId");
			if (addressIdStr.equals("NaN")) {
				return;
			}
			
			int addressId = Integer.parseInt(addressIdStr);
			AddressServer addressServer=new AddressServerImpl();
			int res= addressServer.changeAddressToInVisible(addressId);
			String info="";
			if (res>0) {
				info="删除成功!";
			}else {
				info="删除失败!";
			}
			resp.setContentType("text/plain;charset=utf-8");
			PrintWriter out = resp.getWriter();
			
			out.print(info);
			out.flush();
			out.close();
			
		}else if (opr.equals("setDefault")) {
			String addressIdStr = req.getParameter("addressId");
			String uidStr = req.getParameter("uid");
			int addressId= -1;
			int uid = -1;
			try {
				addressId= Integer.parseInt(addressIdStr);
				uid = Integer.parseInt(uidStr);//转化成整形
			} catch (Exception e) {
				return;
			}
			
			AddressServer addressServer=new AddressServerImpl();
			boolean isSuccess= addressServer.setAddressDefault(addressId, uid);

			resp.setContentType("text/plain;charset=utf-8");
			PrintWriter out = resp.getWriter();
			if (isSuccess) {
				out.print("设置成功!");
			}else {
				out.print("设置失败!");
			}
			out.flush();
			out.close();
			
		}else if (opr.equals("order")) {
			String uidStr= req.getParameter("uid");
			String data= req.getParameter("data");
			String addressIdStr = req.getParameter("addressid");
			//解析 uidStr和addressIdStr
			int uid= Integer.parseInt(uidStr);
			int addressId= Integer.parseInt(addressIdStr);
			double totalPrice=0;
			List<CartItem> cartItems= JSON.parseArray(data, CartItem.class);
			//得到商品信息
			SnackServer snackServer=new SnackServerImpl();
			for (int i = 0; i < cartItems.size(); i++) {
				CartItem cartItem= cartItems.get(i);
				int sid= cartItem.getSid();
				int quantity= cartItem.getQuantity();
				double singlePrice= snackServer.getSinglePriceBySid(sid);
				Snack snack= snackServer.getSnackBySid(sid);
				cartItem.setSinglePrice(singlePrice);
				double itemPrice=ArithUtil.mul(singlePrice, quantity);
				totalPrice=ArithUtil.add(totalPrice, itemPrice);//总价叠加
				cartItem.setItemTotalPrice(itemPrice);
				cartItem.setSnack(snack);
			}
			//得到地址
			AddressServer addressServer=new AddressServerImpl();
			Address address= addressServer.getAddressByAddressId(addressId);
			String addressFinal=address.getProvinceName()+" "+address.getCityName()+" "+address.getAreaName()+" "+
					address.getStreet();
			
			//组装成一个order
			Order order=new Order();
			order.setAddressId(addressId);
			order.setAddressStr(addressFinal);
			order.setCartItems(cartItems);
			
			order.setContactPerson(address.getContactPerson());
			order.setCreateTime(new Date());
			
			order.setPayMoney(totalPrice);
			order.setShouldPayMoney(totalPrice);
			order.setUid(uid);
			order.setPhoneNum(address.getPhoneNum());
			Random random=new Random();
			int seriNum=random.nextInt(2000000000);
			while (seriNum<1000000000) {
				seriNum=random.nextInt(2000000000);
			}
			//随机生成订单编号
			order.setSerialnum(seriNum);
			//下单业务
			UserServer userServer=new UserServerImpl();
			OrderServer orderServer=new OrderServerImpl();
			String info = orderServer.order(order);
			User user=null;
			if (info.equals("购买成功!")) {
				user= userServer.getUserById(uid);
				session.setAttribute("user", user);
			}
			
			session.setAttribute("buyInfo", info);
			resp.setStatus(302);  
		    resp.setHeader("location", "/SnacksProject/onOprator/BuyInfo/buyInfo.jsp");  
	
		}else if (opr.equals("getAllOrders")) {
			String uidStr = req.getParameter("uid");
			int uid= Integer.parseInt(uidStr);
			OrderServer orderServer=new OrderServerImpl();
			List<Order> orders= orderServer.getAllOrdersByUid(uid);
			req.setAttribute("orders", orders);
			req.getRequestDispatcher("/onOprator/Process/processOrderList.jsp").forward(req, resp);
			
		}else if (opr.equals("checkOrder")) {
 
			String oidStr = req.getParameter("oid");
			String uidStr = req.getParameter("uid");
			if (uidStr==null||uidStr=="") {
				return;
			}
			int oid = Integer.parseInt(oidStr);
			int uid = Integer.parseInt(uidStr);
			
			User user= (User)session.getAttribute("user");
			 if (user==null||user.getId()!=uid) {
				return;
			}
			OrderServer orderServer=new OrderServerImpl();
			Order order= orderServer.getOrderByOid(oid);
			
			req.setAttribute("order", order);
			req.getRequestDispatcher("/onOprator/orderDesc/orderMsg.jsp").forward(req, resp);
			
		}else if (opr.equals("waitDeliverOrderList")) {
			//获得所有的待发货的订单
			String uidStr = req.getParameter("uid");
			int uid = Integer.parseInt(uidStr);
			OrderServer orderServer=new OrderServerImpl();
			List<Order> orders= orderServer.getOrdersWaitDeliver(uid);
		
			
			
			req.setAttribute("orders", orders);
			req.getRequestDispatcher("/onOprator/Process/processOrderList.jsp").forward(req, resp);

		}else if (opr.equals("waitCheckOrderList")) {
			//获得所有待收货的订单
			String uidStr = req.getParameter("uid");
			int uid = Integer.parseInt(uidStr);
			OrderServer orderServer=new OrderServerImpl();
			List<Order> orders= orderServer.getOrdersWaitCheck(uid);
			req.setAttribute("orders", orders);
			req.getRequestDispatcher("/onOprator/Process/processOrderList.jsp").forward(req, resp);
		}else if (opr.equals("waitValueOrderList")) {
			String uidStr = req.getParameter("uid");
			int uid = Integer.parseInt(uidStr);
			OrderServer orderServer=new OrderServerImpl();
			List<Order> orders= orderServer.getOrdersWaitValue(uid);
			req.setAttribute("orders", orders);
			req.getRequestDispatcher("/onOprator/Process/processOrderList.jsp").forward(req, resp);
			
		}else if (opr.equals("confirmOrder")) {//操作为确认收货
			String uidStr = req.getParameter("uid");
			String pwd = req.getParameter("pwd");
			pwd = UserServlet.md5ToUpperCase(pwd);
			String serialNumStr= req.getParameter("serialnum");
			int uid = Integer.parseInt(uidStr);
			int serialNum= Integer.parseInt(serialNumStr);
			//先判断密码是否正确。
			UserServer userServer=new UserServerImpl();
			User user= userServer.getUserByPwd(pwd, uid);
			String info="";
			if (user==null) {//密码错误
				info="密码错误，确认收货失败!";
			}else {
				//根据 uid和订单编号去确认收货
				OrderServer orderServer=new OrderServerImpl();
				int res= orderServer.confirmOrder(uid, serialNum);
			
				if (res>0) {
					info="确认成功!";
				}else {
					info="确认失败!";
				}
				
			}
			resp.setContentType("text/plain;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(info);
			out.flush();
			out.close();
			
			
			
			
		}else if (opr.equals("valueOrder")) {//操作为进入订单评价页面
			User user= (User)session.getAttribute("user");
			String oidStr = req.getParameter("oid");
			String uidStr = req.getParameter("uid");
			int uid = Integer.parseInt(uidStr);
			int oid = Integer.parseInt(oidStr);
			if (user==null||uidStr==""||uidStr==null||user.getId()!=uid) {
				return;//非法访问  拒绝访问
			}
			OrderServer orderServer=new OrderServerImpl();
			Order order= orderServer.getOrderByOid(oid);
			req.setAttribute("order", order);
			req.getRequestDispatcher("/onOprator/comment/comment.jsp").forward(req, resp);//进入评价页面
			
		}else if (opr.equals("good")) {
			//点赞操作
			String sidStr = req.getParameter("sid");
			int sid = Integer.parseInt(sidStr);
			OrderServer orderServer=new OrderServerImpl();
			int res= orderServer.good(sid);
			String info="";
			if (res>0) {
				info="点赞成功!";
			}else {
				info="点赞失败!";
			}
			resp.setContentType("text/plain;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(info);
			out.flush();
			out.close();
			
		}else if (opr.equals("doComment")) {//评论
			User user= (User)session.getAttribute("user");
			String uidStr = req.getParameter("uid");
			String oidStr = req.getParameter("oid");
			int uid = Integer.parseInt(uidStr);
			int oid = Integer.parseInt(oidStr);
			if (user==null||uidStr==""||uidStr==null||user.getId()!=uid) {
				return;//非法访问  拒绝访问
			}
			String data = req.getParameter("data");
			List<SnackComment> snackComments= JSON.parseArray(data, SnackComment.class);
			OrderServer orderServer=new OrderServerImpl();
			//拿到了评论对象，看评论内容是否为空， 若为空则设置为默认评论
			boolean isSuc = orderServer.doComment(oid,snackComments);
			String info="";
			if (isSuc) {
				info="评论成功!";
			}else {
				info="评论失败!";
			}
			resp.setContentType("text/plain;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(info);
			out.flush();
			out.close();
		}else if (opr.equals("deleteOrder")) {
			String uidStr = req.getParameter("uid");
			String oidStr = req.getParameter("oid");
			int uid=-1;
			int oid=-1;
			try {
				uid =Integer.parseInt(uidStr);
				oid =Integer.parseInt(oidStr);
			} catch (Exception e) {
				return;
			}
			 User user= (User)req.getSession().getAttribute("user");
			 if (user==null||user.getId()!=uid) {
				return;//非法登录
			}
			 //删除订单  把订单变为不可见
			 OrderServer orderServer=new OrderServerImpl();
			 int res= orderServer.deleteOrder(oid);
			 String info="";
			 if (res>0) {
				info="删除成功!";
			}else {
				info="删除失败!";
			}
			 resp.setContentType("text/plain;charset=utf-8");
				PrintWriter out = resp.getWriter();
				out.print(info);
				out.flush();
				out.close();
			 
			
		}
		
	}

	
	
}
