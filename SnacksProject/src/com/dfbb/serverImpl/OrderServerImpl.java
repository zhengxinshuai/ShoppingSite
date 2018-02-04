package com.dfbb.serverImpl;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.dfbb.common.OrderState;
import com.dfbb.dao.AddressDao;
import com.dfbb.dao.OrderDao;
import com.dfbb.dao.SnackDao;
import com.dfbb.dao.UserDao;
import com.dfbb.daoImpl.AddressDaoImpl;
import com.dfbb.daoImpl.OrderDaoImpl;
import com.dfbb.daoImpl.SnackDaoImpl;
import com.dfbb.daoImpl.UserDaoImpl;
import com.dfbb.entity.Address;
import com.dfbb.entity.CartItem;
import com.dfbb.entity.Order;
import com.dfbb.entity.OrderItem;
import com.dfbb.entity.Page;
import com.dfbb.entity.Snack;
import com.dfbb.entity.SnackComment;
import com.dfbb.server.CartServer;
import com.dfbb.server.OrderServer;
import com.dfbb.tool.ArithUtil;
import com.dfbb.tool.DataSourceTool;

public class OrderServerImpl implements OrderServer{
	OrderDao orderDao=new OrderDaoImpl();
	@Override
	public String order(Order order) {
		Connection connection= DataSourceTool.getConnection();
		
		double payMoney= order.getPayMoney();
		//判断账户余额足不足
		UserDao userDao=new UserDaoImpl();
		double moneyLeft= userDao.getUserMoneyLeft(connection, order.getUid());
		if (moneyLeft>payMoney) {//余额充足
			SnackDao snackDao=new SnackDaoImpl();
			//判断库存
			boolean isEnough=true;
			StringBuffer sb=new StringBuffer();
			List<CartItem> cartItems= order.getCartItems();
			for (int i = 0; i < cartItems.size(); i++) {
				CartItem tempCartItem= cartItems.get(i);
				int quantity= tempCartItem.getQuantity();
				int stocks= snackDao.getStocksBySid(connection, tempCartItem.getSid());
				if (stocks<quantity) {//库存不充足
					isEnough=false;
					sb.append(tempCartItem.getSnack().getSname()+" ");
				}
				
			}
			if (isEnough) {//充足
				//加订单  每个零食减库存  减少余额 清空购物车中指定零食  往orderitem中加item
				try {
					boolean isCutStocksSuc=true;
					OrderDao orderDao=new OrderDaoImpl();
					connection.setAutoCommit(false);
					int resAddOrder=orderDao.addOrder(connection, order);
					
					
					
					//得到这个账户最大的订单编号
					int maxOrderId= orderDao.getUidMaxOid(connection, order.getUid());
					boolean isAddOrderItemSuc=true;
					for (int i = 0; i < cartItems.size(); i++) {
						CartItem tempCartItem= cartItems.get(i);
						int res= orderDao.addOrderItem(connection, tempCartItem, maxOrderId);
						if (res<=0) {
							isAddOrderItemSuc=false;
						}
					}
					
					
					//更新库存
					for (int i = 0; i < cartItems.size(); i++) {
						CartItem tempCartItem= cartItems.get(i);
						int sid= tempCartItem.getSid();
						int quantity= tempCartItem.getQuantity();
						int stocks= snackDao.getStocksBySid(connection, tempCartItem.getSid());
						int leftStocks=stocks-quantity;//剩余的此项零食库存
						int res= snackDao.updateStocks(connection, leftStocks, sid);
						if (res<=0) {
							isCutStocksSuc=false;
						}
					}
					boolean isAddSellQuantitySuc=true;
					//增加销量
					for (int i = 0; i < cartItems.size(); i++) {
						CartItem tempCartItem= cartItems.get(i);
						int sid= tempCartItem.getSid();
						int quantity= tempCartItem.getQuantity();
						int res= orderDao.addSnackQunatity(connection, sid, quantity);
						
						if (res<=0) {
							isAddSellQuantitySuc=false;
						}
					}
					
					
					CartServer cartServer=new CartServerImpl();
					//清空购物车中该零食
					boolean isClearCartSuc=true;
					for (int i = 0; i < cartItems.size(); i++) {
						CartItem tempCartItem= cartItems.get(i);
						tempCartItem.setUid(order.getUid());
						int res=cartServer.deleteRowByUidASid(tempCartItem);
					
						if (res<=0) {
							isClearCartSuc=false;
						}
					}
					double money1=ArithUtil.sub(moneyLeft, payMoney);
					//减少账户余额
					int resUpdateMoney= userDao.updateMoney(connection, order.getUid(), money1);
					//System.out.println("resUpdateMoney:"+resUpdateMoney+",isCutStocksSuc:"+isCutStocksSuc+",resAddOrder:"+resAddOrder+",isClearCartSuc:"+isClearCartSuc+",isAddOrderItemSuc:"+isAddOrderItemSuc);
					if (resUpdateMoney>0&&isCutStocksSuc==true&&resAddOrder>0&&isAddOrderItemSuc==true&&isAddSellQuantitySuc==true) {
						connection.commit();
						return "购买成功!";
					}else {
						connection.rollback();
						return "购买失败";
					}	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					try {
						connection.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return "购买失败";
					
				}finally {
					DataSourceTool.closeAll(connection, null, null);
				}
			}else {//不充足
				return "下单失败!"+sb.toString()+"库存不充足";
			}	
		}else {//余额不足
			return "余额不足，购买失败!";
		}
	}

	@Override
	public List<Order> getAllOrdersByUid(int uid) {
		Connection connection=DataSourceTool.getConnection();
		AddressDao addressDao=new AddressDaoImpl();
		SnackDao snackDao=new SnackDaoImpl();
		List<Order> orders= orderDao.getAllOrdersByUid(connection, uid);
		if (orders.size()==0) {
			return null;
		}
		for (int i = 0; i < orders.size(); i++) {
			Order order= orders.get(i);
			int oid= order.getOid();
			int addressId= order.getAddressId();
			Address address= addressDao.getAddressByAddressId(connection, addressId);
			
			int pid= address.getProvinceid();
			int aid= address.getAreaid();
			int cid= address.getCityid();
			String pName= addressDao.getPNameByPid(connection, pid);
			String cName= addressDao.getCNameByCid(connection, cid);
			String aName=addressDao.getANameByAid(connection, aid);
			address.setProvinceName(pName);
			address.setCityName(cName);
			address.setAreaName(aName);
			address.setWholeAddressStr(pName+" "+cName+" "+aName);
			
			order.setAddress(address);
			//得到这个订单下所有的零食集合
			List<OrderItem> orderItems=orderDao.getOrderItemsByOid(connection, oid);
			//给每个零食设置好对应的snack属性
			for (int j = 0; j < orderItems.size(); j++) {
				OrderItem orderItem= orderItems.get(j);
				int sid= orderItem.getSid();
				Snack snack= snackDao.getSnackBySid(connection, sid);
				orderItem.setSnack(snack);
			}
			order.setOrderItems(orderItems);
			
		}
		DataSourceTool.closeAll(connection, null, null);
		return orders;
	}

	
	public Order getOrderByOid(int oid) {
		Connection connection=DataSourceTool.getConnection();
		AddressDao addressDao=new AddressDaoImpl();
		SnackDao snackDao=new SnackDaoImpl();
		Order order= orderDao.getOrderByOid(connection, oid);
		int addressId= order.getAddressId();
		List<OrderItem> orderItems= orderDao.getOrderItemsByOid(connection, oid);
		
		
		for (int j = 0; j < orderItems.size(); j++) {
			OrderItem orderItem= orderItems.get(j);
			int sid= orderItem.getSid();
			
			Snack snack= snackDao.getSnackBySid(connection, sid);
			orderItem.setSnack(snack);
		}
		Address address=addressDao.getAddressByAddressId(connection, addressId);
		
		int pid= address.getProvinceid();
		int aid= address.getAreaid();
		int cid= address.getCityid();
		String pName= addressDao.getPNameByPid(connection, pid);
		String cName= addressDao.getCNameByCid(connection, cid);
		String aName=addressDao.getANameByAid(connection, aid);
		address.setProvinceName(pName);
		address.setCityName(cName);
		address.setAreaName(aName);
		address.setWholeAddressStr(pName+" "+cName+" "+aName);
	
		
		order.setAddress(address);
		order.setOrderItems(orderItems);
		DataSourceTool.closeAll(connection, null, null);
		return order;
	}

	
	public List<Order> getOrdersWaitDeliver(int uid) {
		Connection connection= DataSourceTool.getConnection();
		AddressDao addressDao=new AddressDaoImpl();
		SnackDao snackDao=new SnackDaoImpl();
		List<Order> orders= orderDao.getOrdersWaitDeliver(connection, uid);
		for (int i = 0; i < orders.size(); i++) {
			Order order= orders.get(i);
			int oid= order.getOid();
			int addressId= order.getAddressId();
			Address address= addressDao.getAddressByAddressId(connection, addressId);

			int pid= address.getProvinceid();
			int aid= address.getAreaid();
			int cid= address.getCityid();
			String pName= addressDao.getPNameByPid(connection, pid);
			String cName= addressDao.getCNameByCid(connection, cid);
			String aName=addressDao.getANameByAid(connection, aid);
			address.setProvinceName(pName);
			address.setCityName(cName);
			address.setAreaName(aName);
			address.setWholeAddressStr(pName+" "+cName+" "+aName);
			
			order.setAddress(address);
			//得到这个订单下所有的零食集合
			List<OrderItem> orderItems=orderDao.getOrderItemsByOid(connection, oid);
			//给每个零食设置好对应的snack属性
			for (int j = 0; j < orderItems.size(); j++) {
				OrderItem orderItem= orderItems.get(j);
				int sid= orderItem.getSid();
				Snack snack= snackDao.getSnackBySid(connection, sid);
				orderItem.setSnack(snack);
			}
			order.setOrderItems(orderItems);
			
		}
		DataSourceTool.closeAll(connection, null, null);
		return orders;
	}

	@Override
	public List<Order> getOrdersWaitCheck(int uid) {
		
		Connection connection= DataSourceTool.getConnection();
		AddressDao addressDao=new AddressDaoImpl();
		SnackDao snackDao=new SnackDaoImpl();
		List<Order> orders= orderDao.getOrdersWaitCheck(connection, uid);
		for (int i = 0; i < orders.size(); i++) {
			Order order= orders.get(i);
			int oid= order.getOid();
			int addressId= order.getAddressId();
			Address address= addressDao.getAddressByAddressId(connection, addressId);

			int pid= address.getProvinceid();
			int aid= address.getAreaid();
			int cid= address.getCityid();
			String pName= addressDao.getPNameByPid(connection, pid);
			String cName= addressDao.getCNameByCid(connection, cid);
			String aName=addressDao.getANameByAid(connection, aid);
			address.setProvinceName(pName);
			address.setCityName(cName);
			address.setAreaName(aName);
			address.setWholeAddressStr(pName+" "+cName+" "+aName);
			
			order.setAddress(address);
			//得到这个订单下所有的零食集合
			List<OrderItem> orderItems=orderDao.getOrderItemsByOid(connection, oid);
			//给每个零食设置好对应的snack属性
			for (int j = 0; j < orderItems.size(); j++) {
				OrderItem orderItem= orderItems.get(j);
				int sid= orderItem.getSid();
				Snack snack= snackDao.getSnackBySid(connection, sid);
				orderItem.setSnack(snack);
			}
			order.setOrderItems(orderItems);
			
		}
		DataSourceTool.closeAll(connection, null, null);
		return orders;
	}

	@Override
	public List<Order> getOrdersWaitValue(int uid) {
		Connection connection= DataSourceTool.getConnection();
		AddressDao addressDao=new AddressDaoImpl();
		SnackDao snackDao=new SnackDaoImpl();
		List<Order> orders= orderDao.getOrdersWaitValue(connection, uid);
		for (int i = 0; i < orders.size(); i++) {
			Order order= orders.get(i);
			int oid= order.getOid();
			int addressId= order.getAddressId();
			Address address= addressDao.getAddressByAddressId(connection, addressId);

			int pid= address.getProvinceid();
			int aid= address.getAreaid();
			int cid= address.getCityid();
			String pName= addressDao.getPNameByPid(connection, pid);
			String cName= addressDao.getCNameByCid(connection, cid);
			String aName=addressDao.getANameByAid(connection, aid);
			address.setProvinceName(pName);
			address.setCityName(cName);
			address.setAreaName(aName);
			address.setWholeAddressStr(pName+" "+cName+" "+aName);
			
			order.setAddress(address);
			//得到这个订单下所有的零食集合
			List<OrderItem> orderItems=orderDao.getOrderItemsByOid(connection, oid);
			//给每个零食设置好对应的snack属性
			for (int j = 0; j < orderItems.size(); j++) {
				OrderItem orderItem= orderItems.get(j);
				int sid= orderItem.getSid();
				Snack snack= snackDao.getSnackBySid(connection, sid);
				orderItem.setSnack(snack);
			}
		
			order.setOrderItems(orderItems);
			
		}
		DataSourceTool.closeAll(connection, null, null);
		return orders;
	}

	@Override
	public int confirmOrder(int uid, int serialNum) {
		Connection connection= DataSourceTool.getConnection();
		int res=orderDao.updateOrderToChecked(connection, uid, serialNum);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}

	@Override
	public int good(int sid) {
		Connection connection=DataSourceTool.getConnection();
		int res= orderDao.goodSnack(connection, sid);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}

	@Override
	public boolean doComment(int oid,List<SnackComment> snackComments) {
		Connection connection= DataSourceTool.getConnection();
		boolean isSuc=false;
		try {
			connection.setAutoCommit(false);
			//给零食评价赋默认值
			int affectRow=0;
			
			for (int i = 0; i < snackComments.size(); i++) {
				SnackComment snackComment= snackComments.get(i);
				String comment = snackComment.getComment();
				
				if (comment.hashCode()==0) {
					comment="默认好评!";
					snackComment.setComment(comment);
				}
				int res= orderDao.commentSnack(connection, snackComment);
				affectRow+=res;	
			}
			//把这个订单修改为已评论
			int res2= orderDao.changeOrderToEvaluted(connection, oid);
			
			
			if (affectRow==snackComments.size()&&res2>0) {
				isSuc=true;
				connection.commit();
			}else {
				connection.rollback();
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(connection, null, null);
		}
		
		
		
		return isSuc;
	}

	@Override
	public int deleteOrder(int oid) {
		Connection connection= DataSourceTool.getConnection();
		int res= orderDao.changeOrderToNotShow(connection, oid);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}
	public static void processOrders(Connection connection,List<Order> orders){
			AddressDao addressDao=new AddressDaoImpl();
			OrderDao orderDao=new OrderDaoImpl();
			SnackDao snackDao=new SnackDaoImpl();
		for (int i = 0; i < orders.size(); i++) {
			Order order= orders.get(i);
			int oid= order.getOid();
			int addressId= order.getAddressId();
			Address address= addressDao.getAddressByAddressId(connection, addressId);
			if(address==null){
				continue;
				
			}
			int pid= address.getProvinceid();
			int aid= address.getAreaid();
			int cid= address.getCityid();
			String pName= addressDao.getPNameByPid(connection, pid);
			String cName= addressDao.getCNameByCid(connection, cid);
			String aName=addressDao.getANameByAid(connection, aid);
			address.setProvinceName(pName);
			address.setCityName(cName);
			address.setAreaName(aName);
			address.setWholeAddressStr(pName+" "+cName+" "+aName);
			order.setAddress(address);
			//得到这个订单下所有的零食集合
			List<OrderItem> orderItems=orderDao.getOrderItemsByOid(connection, oid);
			//给每个零食设置好对应的snack属性
			for (int j = 0; j < orderItems.size(); j++) {
				OrderItem orderItem= orderItems.get(j);
				int sid= orderItem.getSid();
				Snack snack= snackDao.getSnackBySid(connection, sid);
				orderItem.setSnack(snack);
			}
			order.setOrderItems(orderItems);
			
		}
		
	}

	@Override
	public List<Order> getOrdersByCondition(Page page, OrderState orderState) {
		Connection connection= DataSourceTool.getConnection();
		List<Order> orders= orderDao.getOrdersByCondition(connection, page, orderState);
		processOrders(connection, orders);
		DataSourceTool.closeAll(connection, null, null);
		return orders;
	
	}

	/**
	 * 根据条件查找到订单的数量
	 */
	public int getOrdersCount(OrderState orderState) {
		Connection connection=DataSourceTool.getConnection();
		int count= orderDao.getOrdersCount(connection, orderState);
		DataSourceTool.closeAll(connection, null, null);
		return count;
	}

	@Override
	public int deliverOrder(int serinum) {
		Connection connection=DataSourceTool.getConnection();
		int res= orderDao.deliverOrder(connection, serinum);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}
	
}
