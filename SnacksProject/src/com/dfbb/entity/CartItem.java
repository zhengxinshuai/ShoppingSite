package com.dfbb.entity;
/**
 * 购物车里的一项,或者也指订单中的一项
 * @author apple
 *
 */
public class CartItem {
	private int uid;
	private int sid;
	private int quantity;
	private double singlePrice;//该项单价
	private double itemTotalPrice;//乘以数量的总价
	private Snack snack;
	private int isCollect;//0代表未收藏  1代表已收藏
	
	
	
	public int getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}

	public double getSinglePrice() {
		return singlePrice;
	}
	
	public void setSinglePrice(double singlePrice) {
		this.singlePrice = singlePrice;
	}

	public void setItemTotalPrice(double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}

	public double getItemTotalPrice() {
		return itemTotalPrice;
	}
	
	public Snack getSnack() {
		return snack;
	}
	public void setSnack(Snack snack) {
		this.snack = snack;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}
