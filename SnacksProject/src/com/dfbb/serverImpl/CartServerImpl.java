package com.dfbb.serverImpl;


import java.sql.Connection;
import java.util.List;

import com.dfbb.dao.CartDao;
import com.dfbb.daoImpl.CartDaoImpl;
import com.dfbb.entity.CartItem;
import com.dfbb.server.CartServer;
import com.dfbb.tool.DataSourceTool;

public class CartServerImpl implements CartServer{

	CartDao cartDao=new CartDaoImpl();
	
	
	
	public CartItem getCartItemBysidAuid(int sid,int uid) {
		Connection connection= DataSourceTool.getConnection();
		CartItem cartItem= cartDao.getCartItemBySid(connection, sid,uid);
		DataSourceTool.closeAll(connection, null, null);
		return cartItem;
	}



	@Override
	public int addToCart(CartItem cartItem) {
		Connection connection= DataSourceTool.getConnection();
		int res=cartDao.addToCart(connection, cartItem);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}



	@Override
	public int addNumToCart(CartItem cartItem) {
		Connection connection= DataSourceTool.getConnection();
		int res=cartDao.addNumToCart(connection, cartItem);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}



	@Override
	public List<CartItem> getCartItemsByUid(int uid) {
		Connection connection= DataSourceTool.getConnection();
		List<CartItem> cartItems= cartDao.getCartItemsByUid(connection, uid);
		DataSourceTool.closeAll(connection, null, null);
		return cartItems;
	}



	@Override
	public int deleteRowByUidASid(CartItem cartItem) {
		Connection connection=DataSourceTool.getConnection();
		int res= cartDao.deleteRowByUidASid(connection, cartItem);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}



	@Override
	public int updateCart(CartItem cartItem) {
		Connection connection=DataSourceTool.getConnection();
		int res= cartDao.updateCart(connection, cartItem);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}

}
