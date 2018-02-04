package com.dfbb.server;

import java.sql.Connection;
import java.util.List;

import com.dfbb.common.OrderState;
import com.dfbb.entity.Order;
import com.dfbb.entity.Page;
import com.dfbb.entity.SnackComment;

public interface OrderServer {
	
	String order(Order order);
	
	List<Order> getAllOrdersByUid(int uid);
	
	Order getOrderByOid(int oid);
	
	List<Order> getOrdersWaitDeliver(int uid);
	
	List<Order> getOrdersWaitCheck(int uid);
	
	List<Order> getOrdersWaitValue(int uid);
	
	int confirmOrder(int uid, int serialNum);
	
	int good(int sid);
	
	boolean doComment(int oid,List<SnackComment> snackComments);
	
	int deleteOrder(int oid);
	
	List<Order> getOrdersByCondition(Page page,OrderState orderState);
	
	int getOrdersCount(OrderState orderState);
	
	int deliverOrder(int serinum);
}
