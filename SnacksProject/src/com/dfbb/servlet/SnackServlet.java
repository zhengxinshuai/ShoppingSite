package com.dfbb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.j2ee.statistics.Statistic;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import com.alibaba.fastjson.JSON;
import com.dfbb.common.OrderByWhat;
import com.dfbb.entity.CartItem;
import com.dfbb.entity.CollectionItem;
import com.dfbb.entity.Page;
import com.dfbb.entity.Snack;
import com.dfbb.entity.SnackComment;
import com.dfbb.entity.SnackPicture;
import com.dfbb.entity.User;
import com.dfbb.server.CartServer;
import com.dfbb.server.CollectionServer;
import com.dfbb.server.SnackServer;
import com.dfbb.serverImpl.CartServerImpl;
import com.dfbb.serverImpl.CollectionServerImpl;
import com.dfbb.serverImpl.SnackServerImpl;

public class SnackServlet extends HttpServlet{

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
			return;
		}
		if (opr.equals("snackDetail")) {
			String sidStr= req.getParameter("sid");
			SnackServer snackServer=new SnackServerImpl();
			int sid = Integer.parseInt(sidStr);
			Snack snack = snackServer.getSnackBySid(sid);
			List<SnackPicture> snackPictures= snackServer.getSnackPicturesBySid(sid);
			List<SnackPicture> mainSnackPictures=new ArrayList<SnackPicture>();
			
			for (int i = 0; i < snackPictures.size(); i++) {
				SnackPicture tempPicture = snackPictures.get(i);
				if (tempPicture.getIsMain().equals("Y")) {
					mainSnackPictures.add(tempPicture);
				}
			}
			User user= (User)session.getAttribute("user");
			CollectionServer collectionServer=new CollectionServerImpl();
			if (user==null) {
				return;
			}
			CollectionItem collectionItem=collectionServer.getCollectionItemByUidASid(user.getId(), sid);
			int isTheSidCollect=0;
			if (collectionItem==null) {
				isTheSidCollect=0;//这个零食被收藏了吗
			}else {
				isTheSidCollect=1;
			}
			//得到该零食所有的评论
			List<SnackComment> snackComments= snackServer.getSnackCommentsBySid(sid);
			session.setAttribute("snack", snack);
			session.setAttribute("mainSnackPictures", mainSnackPictures);
			session.setAttribute("snackPictures", snackPictures);
			session.setAttribute("snackComments", snackComments);
			session.setAttribute("isTheSidCollect", isTheSidCollect);
			//转发到零食详情页面
			req.getRequestDispatcher("onOprator/snackDesc/SnacksDesc.jsp").forward(req, resp);
			
			
			
		}else if (opr.equals("search")) {//当操作为搜索时
			String keyword = req.getParameter("keyword");
			String orderType = req.getParameter("orderType");//排序规则
			String pNumStr= req.getParameter("pNum");
			
			OrderByWhat orderByWhat=null;
			
			switch (orderType) {
			case "PriceAsc":
				orderByWhat=OrderByWhat.PriceAsc;
				break;
			case "PriceDesc":
				orderByWhat=OrderByWhat.PriceDesc;
				break;
			case "PraiseAsc":
				orderByWhat=OrderByWhat.PraiseAsc;
				break;
			case "PraiseDesc":
				orderByWhat=OrderByWhat.PraiseDesc;
				break;
			case "DateAsc":
				orderByWhat=OrderByWhat.DateAsc;
				break;
			case "DateDesc":
				orderByWhat=OrderByWhat.DateDesc;
				break;
			case "SellQuntityAsc":
				orderByWhat=OrderByWhat.SellQuntityAsc;
				break;
			case "SellQuntityDesc":
				orderByWhat=OrderByWhat.SellQuntityDesc;
				break;
			default:
				orderByWhat=OrderByWhat.SellQuntityDesc;//默认使用销量降序排序
				break;
			}
			SnackServer snackServer=new SnackServerImpl();
			
			int pNum=-1;
			if (pNumStr==null) {
				pNum=1;
			}else {
				pNum=Integer.parseInt(pNumStr);
			}
			
			
			int count=snackServer.getSnackCountByKeyWord(keyword);
			Page page=new Page();
			page.setPerPageCount(4);
			page.setTotalItemCount(count);
			page.setCurrentPageNo(pNum);
			List<Snack> snacks= snackServer.querySnack(page, orderByWhat, keyword);
			String snackJson= JSON.toJSONStringWithDateFormat(snacks, "yyyy-MM-dd HH:mm:ss");
			page.setSnackJson(snackJson);
			
			String pageJson = JSON.toJSONString(page);
			
			
			resp.setContentType("application/json;charset=utf-8");  
			PrintWriter out= resp.getWriter();
			out.write(pageJson);
			out.flush();
			out.close();
		}else if (opr.equals("enterSearch")) {//当页面是在其他的页面发起搜索时，先进入搜索页面，再通过ajax得到搜索到的零食信息
			String keyword = req.getParameter("keyword");
			req.setAttribute("keyword", keyword);
			req.getRequestDispatcher("/onOprator/searchSnack/searchSnack.jsp").forward(req, resp);//转发至零食搜索界面
		}else if (opr.equals("addToCart")) {//向加入指定的数量
			//加入购物车
			String sidStr = req.getParameter("sid");
			String goodNumStr = req.getParameter("goodNum");
			String uidStr=req.getParameter("uid");
			int sid= Integer.parseInt(sidStr);
			int uid=Integer.parseInt(uidStr);
			int quantity=Integer.parseInt(goodNumStr);
			CartServer cartServer=new CartServerImpl();
			CartItem cartItem= cartServer.getCartItemBysidAuid(sid,uid);
			
			CartItem addItem=new CartItem();
			addItem.setSid(sid);
			addItem.setUid(uid);
			addItem.setQuantity(quantity);
			String info="";
			int res1=-1;
			int res2=-1;
			if (cartItem==null) {
				//如果当前用户的购物车中没有这个零食，则加入
				res1=cartServer.addToCart(addItem);
				
			}else {//当前用户已经有了这个零食，则增加零食数量
			
				res2=cartServer.addNumToCart(addItem);
			}
			
			if (res1>0||res2>0) {
				info="加入成功!";
			}else {
				info="加入失败!";
			}
			resp.setContentType("text/plain;charset=utf-8");
			PrintWriter out = resp.getWriter();
			
			out.print(info);
			out.flush();
			out.close();
			
			
			
		}else if (opr.equals("checkCart")) {//当操作为查看购物车时
			
			String uidStr = req.getParameter("uid");
			int uid= Integer.parseInt(uidStr);
			
			 User user= (User)session.getAttribute("user");
			
			 if (user==null) {
				return;
			}
			 int id= user.getId();
			 if (uid!=id) {
				return;
			}
			CartServer cartServer=new CartServerImpl();
			SnackServer snackServer=new SnackServerImpl();
			
			List<CartItem> cartItems= cartServer.getCartItemsByUid(uid);
			for (int i = 0; i < cartItems.size(); i++) {//为每一个的cartItem赋值
				CartItem cartItem= cartItems.get(i);
				int sid= cartItem.getSid();
				Snack snack= snackServer.getSnackBySid(sid);
//				CartItem cartItem2= cartServer.getCartItemBysidAuid(sid, uid);
				//得到收藏
				CollectionServer collectionServer=new CollectionServerImpl();
				CollectionItem collection= collectionServer.getCollectionItemByUidASid(uid, sid);
				if (collection==null) {
					cartItem.setIsCollect(0);//该条目未收藏
				}else {
					cartItem.setIsCollect(1);//该条目已收藏
				}
				cartItem.setSnack(snack);
			}
			
			session.setAttribute("cartItems", cartItems);
			
			
			//控制不缓存
			resp.setHeader( "Pragma", "no-cache" );
			resp.addHeader( "Cache-Control", "must-revalidate" );
			resp.addHeader( "Cache-Control", "no-cache" );
			resp.addHeader( "Cache-Control", "no-store" );
			resp.setDateHeader("Expires", 0); 
			req.getRequestDispatcher("/onOprator/shoppingCart/shopping_cart.jsp").forward(req, resp);
//			resp.setStatus(302);  
//	        resp.setHeader("location", "/SnacksProject/onOprator/shoppingCart/shopping_cart.jsp");  
			
		}else if (opr.equals("deleteCart")) {//当操作为删除购物车中一条的时候
			String sidStr = req.getParameter("sid");
			String uidStr=req.getParameter("uid");
			int sid= Integer.parseInt(sidStr);
			int uid=Integer.parseInt(uidStr);
			CartItem cartItem=new CartItem();
			cartItem.setSid(sid);
			cartItem.setUid(uid);
			
			CartServer cartServer=new CartServerImpl();
			int res=cartServer.deleteRowByUidASid(cartItem);
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
		}else if (opr.equals("updateCart")) {
			String sidStr = req.getParameter("sid");
			String uidStr=req.getParameter("uid");
			String numStr=req.getParameter("quantity");
			int sid= Integer.parseInt(sidStr);
			int uid=Integer.parseInt(uidStr);
			int quantity=Integer.parseInt(numStr);
			CartItem cartItem=new CartItem();
			cartItem.setSid(sid);
			cartItem.setUid(uid);
			cartItem.setQuantity(quantity);
			CartServer cartServer=new CartServerImpl();
			int res=cartServer.updateCart(cartItem);
			String info="";
			if (res>0) {
				info="更新成功!";
			}else {
				info="更新失败!";
			}
			resp.setContentType("text/plain;charset=utf-8");
			PrintWriter out = resp.getWriter();
			
			out.print(info);
			out.flush();
			out.close();
		}else if (opr.equals("checkCollection")) {
			String uidStr = req.getParameter("uid");
			
			int uid= Integer.parseInt(uidStr);
			
			User user= (User)session.getAttribute("user");
			
			 if (user==null) {
				return;
			}
			 int id= user.getId();
			 if (uid!=id) {
				return;
			}
			
			CollectionServer collectionServer=new CollectionServerImpl();
			List<CollectionItem> collectionItems= collectionServer.getCollectionItemsByUid(uid);
			
			session.setAttribute("collectionItems", collectionItems);
			resp.setStatus(302);  
		    resp.setHeader("location", "/SnacksProject/onOprator/collection/collection.jsp");  
			
			
		}else if (opr.equals("deleteCollection")) {
			String uidStr = req.getParameter("uid");
			String sidStr = req.getParameter("sid");
			int uid= Integer.parseInt(uidStr);
			int sid= Integer.parseInt(sidStr);
			User user= (User)session.getAttribute("user");
			 if (user==null) {
				return;
			}
			 int id= user.getId();
			 if (uid!=id) {
				return;
			}
			 //删除收藏条目的业务类
			CollectionServer collectionServer=new CollectionServerImpl();
			int res=collectionServer.deleteCollectionByUidASid(uid, sid);
			String info="";
			if (res>0) {
				info="删除成功!";
				List<CollectionItem> collectionItems= collectionServer.getCollectionItemsByUid(uid);
				session.setAttribute("collectionItems", collectionItems);
				
				
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
