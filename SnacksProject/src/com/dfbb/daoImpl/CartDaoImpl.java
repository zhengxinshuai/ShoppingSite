package com.dfbb.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dfbb.dao.CartDao;
import com.dfbb.entity.CartItem;
import com.dfbb.tool.DataSourceTool;

public class CartDaoImpl implements CartDao{

	/**
	 * 根据sid得到零食
	 */
	public CartItem getCartItemBySid(Connection connection, int sid,int uid) {
		String sql="select * from shoppingCart where sid=? and uid=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		CartItem cartItem=null;
		try {
			ps=connection.prepareStatement(sql);
			ps.setInt(1, sid);
			ps.setInt(2, uid);
			rs= ps.executeQuery();
			if (rs.next()) {
				int uid1= rs.getInt("uid");
				int sid1= rs.getInt("sid");
				int quantity= rs.getInt("quantity");
				cartItem=new CartItem();
				cartItem.setQuantity(quantity);
				cartItem.setSid(sid1);
				cartItem.setUid(uid1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return cartItem;
	}

	@Override
	public int addToCart(Connection connection, CartItem cartItem) {
		String sql="insert into shoppingCart (`uid`,`sid`,`quantity`) values(?,?,?)";
		Object[] params={cartItem.getUid(),cartItem.getSid(),cartItem.getQuantity()};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public int addNumToCart(Connection connection, CartItem cartItem) {
		String sql="update shoppingCart set quantity=quantity+? where uid=? and sid=?";
		Object[] params={cartItem.getQuantity(),cartItem.getUid(),cartItem.getSid()};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
		
	}

	/**
	 * 根据uid找到所有此账户下的items
	 */
	public List<CartItem> getCartItemsByUid(Connection connection, int uid) {
		String sql="select * from shoppingCart where uid=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<CartItem> cartItems=new ArrayList<CartItem>();
		
		try {
			ps=connection.prepareStatement(sql);
			ps.setInt(1, uid);
			
			rs= ps.executeQuery();
			while (rs.next()) {
				int uid1= rs.getInt("uid");
				int sid1= rs.getInt("sid");
				int quantity= rs.getInt("quantity");
				CartItem cartItem=new CartItem();
				cartItem.setQuantity(quantity);
				cartItem.setSid(sid1);
				cartItem.setUid(uid1);
				cartItems.add(cartItem);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return cartItems;
	}

	@Override
	public int deleteRowByUidASid(Connection connection, CartItem cartItem) {
		String sql="delete from shoppingCart where uid=? and sid=?";
		Object[] params={cartItem.getUid(),cartItem.getSid()};
		int row= DataSourceTool.exceuteUpdate(connection, sql, params);
		return row;
	}

	@Override
	public int updateCart(Connection connection, CartItem cartItem) {
		String sql="update shoppingCart set quantity=? where uid=? and sid=?";
		Object[] params={cartItem.getQuantity(),cartItem.getUid(),cartItem.getSid()};
		int row= DataSourceTool.exceuteUpdate(connection, sql, params);
		return row;
	}

}
