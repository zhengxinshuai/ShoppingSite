package com.dfbb.dao;

import java.sql.Connection;
import java.util.List;

import com.dfbb.entity.CartItem;

public interface CartDao {
	//根据sid得到CartItem
	CartItem getCartItemBySid(Connection connection,int sid,int uid);
	
	int addToCart(Connection connection,CartItem cartItem);
	//已经存在了，只需要加数量就好
	int addNumToCart(Connection connection,CartItem cartItem);
	
	List<CartItem> getCartItemsByUid(Connection connection,int uid);
	//根据uid和sid删除一行
	int deleteRowByUidASid(Connection connection,CartItem cartItem);
	/**
	 * 直接根据数量去修改这个购物车条目
	 * @param connection
	 * @return
	 */
	int updateCart(Connection connection,CartItem cartItem);
}
