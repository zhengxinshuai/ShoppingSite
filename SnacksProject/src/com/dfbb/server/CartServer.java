package com.dfbb.server;

import java.util.List;

import com.dfbb.entity.CartItem;

public interface CartServer {
	
	CartItem getCartItemBysidAuid(int sid,int uid);
	int addToCart(CartItem cartItem);
	//已经存在这一项了，只需要增加数量就好
	int addNumToCart(CartItem cartItem);
	
	int deleteRowByUidASid(CartItem cartItem);
	List<CartItem> getCartItemsByUid(int uid);
	/**
	 * 直接把一个购物车条目更新至指定的数量
	 * @param cartItem
	 * @return
	 */
	int updateCart(CartItem cartItem);
}
