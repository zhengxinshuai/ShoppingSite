package com.dfbb.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.NewsAddress;

import com.dfbb.common.OrderState;
import com.dfbb.dao.OrderDao;
import com.dfbb.entity.Address;
import com.dfbb.entity.CartItem;
import com.dfbb.entity.Order;
import com.dfbb.entity.OrderItem;
import com.dfbb.entity.Page;
import com.dfbb.entity.SnackComment;
import com.dfbb.server.AddressServer;
import com.dfbb.serverImpl.AddressServerImpl;
import com.dfbb.tool.DataSourceTool;

public class OrderDaoImpl implements OrderDao{

	/**
	 * 加入一条订单
	 */
	public int addOrder(Connection connection, Order order) {
		String sql="insert into `order`(`uid`,`createtime`,`shouldpayMoney`,`payMoney`,`addressid`,`serialnum`) values (?,?,?,?,?,?)";
		Object[] params={order.getUid(),order.getCreateTime(),order.getShouldPayMoney(),order.getPayMoney(),order.getAddressId(),order.getSerialnum()};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public int getUidMaxOid(Connection connection, int uid) {
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select max(oid) 'oid1' from `order` where uid=?";
		int oid=-1;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, uid);
			rs=ps.executeQuery();
			if(rs.next()){
				oid=rs.getInt("oid1");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		
		return oid;
	}

	@Override
	public int addOrderItem(Connection connection, CartItem cartItem,int oid) {
		String sql="insert into orderitem (`oid`,`sid`,`quantity`,`count`) values (?,?,?,?)";
		Object[] params={oid,cartItem.getSid(),cartItem.getQuantity(),cartItem.getItemTotalPrice()};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	/**
	 * 得到一个用户下所有的订单 
	 */
	public List<Order> getAllOrdersByUid(Connection connection, int uid) {
		String sql="select * from `order` where uid=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Order> orders=new ArrayList<Order>();;
		
		try {
			ps=connection.prepareStatement(sql);
			ps.setInt(1, uid);
			rs= ps.executeQuery();
			while (rs.next()) {
				//在外面业务层设置address和零食
				Order order=new Order();
				int oid= rs.getInt("oid");
				int uid1= rs.getInt("uid");
				Date createTime=rs.getTimestamp("createtime");
				String orderState = rs.getString("orderstate");
				String commentState = rs.getString("commentstate");
				double shouldPayMoney=rs.getDouble("shouldpayMoney");
				double payMoney= rs.getDouble("payMoney");
				int addressId= rs.getInt("addressid");
				Date deliverTime = rs.getTimestamp("delivertime");
				Date checkTime = rs.getTimestamp("checktime");
				int serialNum= rs.getInt("serialnum");
				String isevaluate= rs.getString("isevaluate");
				String isShow = rs.getString("isShow");
				order.setOid(oid);
				order.setUid(uid1);
				order.setCreateTime(createTime);
				order.setOrderState(orderState);
				order.setCommentState(commentState);
				order.setShouldPayMoney(shouldPayMoney);
				order.setPayMoney(payMoney);
				order.setAddressId(addressId);
				order.setDeliverTime(deliverTime);
				order.setCheckTime(checkTime);
				order.setSerialnum(serialNum);
				order.setIsEvaluate(isevaluate);
				order.setIsShow(isShow);
				orders.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		
		return orders;
	}

	@Override
	public List<OrderItem> getOrderItemsByOid(Connection connection, int oid) {
		String sql="select * from `orderitem` where oid=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		
		try {
			ps=connection.prepareStatement(sql);
			ps.setInt(1, oid);
			rs= ps.executeQuery();
			while (rs.next()) {
				//在外面业务层设置address和零食
				OrderItem orderItem=new OrderItem();
				int oid1= rs.getInt("oid");
				int sid = rs.getInt("sid");
				int quantity= rs.getInt("quantity");
				double itemCount= rs.getDouble("count");
				
				orderItem.setOid(oid1);
				orderItem.setSid(sid);
				orderItem.setQuantity(quantity);
				orderItem.setItemCount(itemCount);
				orderItems.add(orderItem);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		
		return orderItems;
	}

	@Override
	public Order getOrderByOid(Connection connection, int oid) {
		String sql="select * from `order` where oid=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		Order order=null;
		
		try {
			ps=connection.prepareStatement(sql);
			ps.setInt(1, oid);
			rs= ps.executeQuery();
			if (rs.next()) {
				//在外面业务层设置address和零食
				order=new Order();
				int oid1= rs.getInt("oid");
				int uid1= rs.getInt("uid");
				Date createTime=rs.getTimestamp("createtime");
			
				
				
				String orderState = rs.getString("orderstate");
				
				String commentState = rs.getString("commentstate");
				double shouldPayMoney=rs.getDouble("shouldpayMoney");
				double payMoney= rs.getDouble("payMoney");
				int addressId= rs.getInt("addressid");
				Date deliverTime = rs.getTimestamp("delivertime");
				Date checkTime = rs.getTimestamp("checktime");
				int serialNum= rs.getInt("serialnum");
				String isevaluate= rs.getString("isevaluate");
				String isShow = rs.getString("isShow");
				order.setOid(oid1);
				order.setUid(uid1);
				order.setCreateTime(createTime);
				order.setOrderState(orderState);
				order.setCommentState(commentState);
				order.setShouldPayMoney(shouldPayMoney);
				order.setPayMoney(payMoney);
				order.setAddressId(addressId);
				order.setDeliverTime(deliverTime);
				order.setCheckTime(checkTime);
				order.setSerialnum(serialNum);
				order.setIsEvaluate(isevaluate);
				order.setIsShow(isShow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		
		return order;
	}

	@Override
	public List<Order> getOrdersWaitDeliver(Connection connection, int uid) {
		String sql="select * from `order` where uid=? and orderstate='未发货'";
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Order> orders=new ArrayList<Order>();;
		
		try {
			ps=connection.prepareStatement(sql);
			ps.setInt(1, uid);
			rs= ps.executeQuery();
			while (rs.next()) {
				//在外面业务层设置address和零食
				Order order=new Order();
				int oid= rs.getInt("oid");
				int uid1= rs.getInt("uid");
				Date createTime=rs.getTimestamp("createtime");
				String orderState = rs.getString("orderstate");
				String commentState = rs.getString("commentstate");
				double shouldPayMoney=rs.getDouble("shouldpayMoney");
				double payMoney= rs.getDouble("payMoney");
				int addressId= rs.getInt("addressid");
				Date deliverTime = rs.getTimestamp("delivertime");
				Date checkTime = rs.getTimestamp("checktime");
				int serialNum= rs.getInt("serialnum");
				String isevaluate= rs.getString("isevaluate");
				String isShow = rs.getString("isShow");
				order.setOid(oid);
				order.setUid(uid1);
				order.setCreateTime(createTime);
				order.setOrderState(orderState);
				order.setCommentState(commentState);
				order.setShouldPayMoney(shouldPayMoney);
				order.setPayMoney(payMoney);
				order.setAddressId(addressId);
				order.setDeliverTime(deliverTime);
				order.setCheckTime(checkTime);
				order.setSerialnum(serialNum);
				order.setIsEvaluate(isevaluate);
				order.setIsShow(isShow);
				orders.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		
		return orders;
	}

	@Override
	public List<Order> getOrdersWaitCheck(Connection connection, int uid) {
		String sql="select * from `order` where uid=? and orderstate='待收货'";
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Order> orders=new ArrayList<Order>();;
		
		try {
			ps=connection.prepareStatement(sql);
			ps.setInt(1, uid);
			rs= ps.executeQuery();
			while (rs.next()) {
				//在外面业务层设置address和零食
				Order order=new Order();
				int oid= rs.getInt("oid");
				int uid1= rs.getInt("uid");
				Date createTime=rs.getTimestamp("createtime");
				String orderState = rs.getString("orderstate");
				String commentState = rs.getString("commentstate");
				double shouldPayMoney=rs.getDouble("shouldpayMoney");
				double payMoney= rs.getDouble("payMoney");
				int addressId= rs.getInt("addressid");
				Date deliverTime = rs.getTimestamp("delivertime");
				Date checkTime = rs.getTimestamp("checktime");
				int serialNum= rs.getInt("serialnum");
				String isevaluate= rs.getString("isevaluate");
				String isShow = rs.getString("isShow");
				order.setOid(oid);
				order.setUid(uid1);
				order.setCreateTime(createTime);
				order.setOrderState(orderState);
				order.setCommentState(commentState);
				order.setShouldPayMoney(shouldPayMoney);
				order.setPayMoney(payMoney);
				order.setAddressId(addressId);
				order.setDeliverTime(deliverTime);
				order.setCheckTime(checkTime);
				order.setSerialnum(serialNum);
				order.setIsEvaluate(isevaluate);
				order.setIsShow(isShow);
				orders.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		
		return orders;
	}

	@Override
	public List<Order> getOrdersWaitValue(Connection connection, int uid) {
		String sql="select * from `order` where uid=? and orderstate='交易成功' and isevaluate='N'";
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Order> orders=new ArrayList<Order>();;
		
		try {
			ps=connection.prepareStatement(sql);
			ps.setInt(1, uid);
			rs= ps.executeQuery();
			while (rs.next()) {
				//在外面业务层设置address和零食
				Order order=new Order();
				int oid= rs.getInt("oid");
				int uid1= rs.getInt("uid");
				Date createTime=rs.getTimestamp("createtime");
				String orderState = rs.getString("orderstate");
				String commentState = rs.getString("commentstate");
				double shouldPayMoney=rs.getDouble("shouldpayMoney");
				double payMoney= rs.getDouble("payMoney");
				int addressId= rs.getInt("addressid");
				Date deliverTime = rs.getTimestamp("delivertime");
				Date checkTime = rs.getTimestamp("checktime");
				int serialNum= rs.getInt("serialnum");
				String isevaluate= rs.getString("isevaluate");
				String isShow = rs.getString("isShow");
				order.setOid(oid);
				order.setUid(uid1);
				order.setCreateTime(createTime);
				order.setOrderState(orderState);
				order.setCommentState(commentState);
				order.setShouldPayMoney(shouldPayMoney);
				order.setPayMoney(payMoney);
				order.setAddressId(addressId);
				order.setDeliverTime(deliverTime);
				order.setCheckTime(checkTime);
				order.setSerialnum(serialNum);
				order.setIsEvaluate(isevaluate);
				order.setIsShow(isShow);
				orders.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return orders;
	}

	@Override
	public int updateOrderToChecked(Connection connection, int uid, int serialNum) {
		String sql="update `order` set orderstate=?,checktime=? where uid=? and serialNum=?";
		Object[] params={"交易成功",new Date(),uid,serialNum};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public int goodSnack(Connection connection, int sid) {
		String sql="update snacks set praise=praise+1 where sid=?";
		Object[] params={sid};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	/**
	 * 插入一条评论
	 */
	public int commentSnack(Connection connection, SnackComment snackComment) {
		String sql="insert into snackscomment (`sid`,`uid`,`dateTime`,`comment`) values (?,?,?,?)";
		Object[] params={snackComment.getSid(),snackComment.getUid(),snackComment.getDate(),snackComment.getComment()};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public int changeOrderToEvaluted(Connection connection, int oid) {
		String sql="update `order` set isevaluate='Y' where oid=?";
		Object[] params={oid};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public int changeOrderToNotShow(Connection connection, int oid) {
		String sql="update `order` set isShow='N' where oid=?";
		Object[] params={oid};
		int res=DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public int addSnackQunatity(Connection connection, int sid, int qunatity) {
		String sql="update `snacks` set sellQuntity=sellQuntity+? where sid=?";
		Object[] params={qunatity,sid};
		int res=DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	/**
	 * 根据条件查询订单
	 */
	public List<Order> getOrdersByCondition(Connection connection, Page page, OrderState orderState) {
		String sql=null;
		int perPageCount= page.getPerPageCount();//得到每一页的数量
		int index=page.getStartIndex();//得到开始的下标
		switch (orderState) {
		case WEIFAHUO:
			sql="select * from `order` where orderstate='未发货' order by createtime desc limit ?,?";
			break;
		case YIFAHUO:
			sql="select * from `order` where orderstate='待收货' order by createtime desc limit ?,?";
			break;
		 case YISHOUHUO:
			 sql="select * from `order` where orderstate='交易成功' order by createtime desc limit ?,?";
			 break;
		 case ALL:
			 sql="select * from `order` order by createtime desc limit ?,?";
			 break;
		}
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Order> orders=new ArrayList<Order>();
		try {
			ps =connection.prepareStatement(sql);
			ps.setInt(1, index);
			ps.setInt(2, perPageCount);
			rs= ps.executeQuery();
			while (rs.next()) {
				//在外面业务层设置address和零食
				Order order=new Order();
				int oid= rs.getInt("oid");
				int uid1= rs.getInt("uid");
				Date createTime=rs.getTimestamp("createtime");
				String orderState1 = rs.getString("orderstate");
				String commentState = rs.getString("commentstate");
				double shouldPayMoney=rs.getDouble("shouldpayMoney");
				double payMoney= rs.getDouble("payMoney");
				int addressId= rs.getInt("addressid");
				Date deliverTime = rs.getTimestamp("delivertime");
				Date checkTime = rs.getTimestamp("checktime");
				int serialNum= rs.getInt("serialnum");
				String isevaluate= rs.getString("isevaluate");
				String isShow = rs.getString("isShow");
				order.setOid(oid);
				order.setUid(uid1);
				order.setCreateTime(createTime);
				order.setOrderState(orderState1);
				order.setCommentState(commentState);
				order.setShouldPayMoney(shouldPayMoney);
				order.setPayMoney(payMoney);
				order.setAddressId(addressId);
				order.setDeliverTime(deliverTime);
				order.setCheckTime(checkTime);
				order.setSerialnum(serialNum);
				order.setIsEvaluate(isevaluate);
				order.setIsShow(isShow);
				orders.add(order);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}	
		return orders;
	}

	@Override
	public int getOrdersCount(Connection connection, OrderState orderState) {
		String sql="";
		switch (orderState) {
		case WEIFAHUO:
			sql="select count(1) 'num' from `order` where orderstate='未发货'";
			break;
		case YIFAHUO:
			sql="select count(1) 'num' from `order` where orderstate='待收货'";
			break;
		case YISHOUHUO:
			sql="select count(1) 'num' from `order` where orderstate='交易成功'";
			break;
		case ALL:
			sql="select count(1) 'num' from `order`";
			break;
		}
		PreparedStatement ps=null;
		ResultSet rs=null;
		int count=-1;
		try {
			ps=connection.prepareStatement(sql);
			rs= ps.executeQuery();
			if (rs.next()) {
				count=rs.getInt("num");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		
		
		
		return count;
	}

	@Override
	public int deliverOrder(Connection connection, int serinum) {
		String sql="update `order` set orderstate='待收货',delivertime=? where serialnum=?";
		Object[] params={new Date(),serinum};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);

		return res;
	}
	
}
