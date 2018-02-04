package com.dfbb.dao;

import java.sql.Connection;
import java.util.List;

import com.dfbb.common.OrderState;
import com.dfbb.entity.CartItem;
import com.dfbb.entity.Order;
import com.dfbb.entity.OrderItem;
import com.dfbb.entity.Page;
import com.dfbb.entity.SnackComment;

public interface OrderDao {
	/**
	 * 将一个订单插入到数据库
	 * @param connection
	 * @param order
	 * @return
	 */
	int addOrder(Connection connection,Order order);
	
	int getUidMaxOid(Connection connection,int uid);
	
	int addOrderItem(Connection connection,CartItem cartItem,int oid);
	/**
	 * 得到一个用户下所有的订单
	 * @return
	 */
	List<Order> getAllOrdersByUid(Connection connection,int uid);
	
	List<OrderItem> getOrderItemsByOid(Connection connection,int oid);
	
	Order getOrderByOid(Connection connection,int oid);
	
	List<Order> getOrdersWaitDeliver(Connection connection,int uid);
	
	List<Order> getOrdersWaitCheck(Connection connection,int uid);
	
	List<Order> getOrdersWaitValue(Connection connection,int uid);
	/**
	 * 确认收货一个订单
	 * @param connection
	 * @param uid
	 * @param serialNum
	 * @return
	 */
	int updateOrderToChecked(Connection connection,int uid,int serialNum);
	/**
	 * 点赞零食
	 * @param connection
	 * @param sid
	 * @return
	 */
	int goodSnack(Connection connection,int sid);
	/**
	 * 给零食评价
	 * @param connection
	 * @param uid
	 * @param snackComments
	 * @return
	 */
	int commentSnack(Connection connection,SnackComment snackComment);
	/**
	 * 给一个订单修改为已评价
	 * @param connection
	 * @param oid
	 * @return
	 */
	int changeOrderToEvaluted(Connection connection,int oid);
	
	int changeOrderToNotShow(Connection connection,int oid);
	
	int addSnackQunatity(Connection connection,int sid,int qunatity);
	
	List<Order> getOrdersByCondition(Connection connection,Page page,OrderState orderState);

	int getOrdersCount(Connection connection,OrderState orderState);
	/**
	 * 发货操作
	 * @param connection
	 * @param serinum
	 * @return
	 */
	int deliverOrder(Connection connection,int serinum);

}
