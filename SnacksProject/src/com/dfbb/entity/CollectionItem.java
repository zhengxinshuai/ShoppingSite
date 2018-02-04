package com.dfbb.entity;
/**
 * 购物车中的一项
 * @author apple
 *
 */
public class CollectionItem {
	
	private int sid;
	private int uid;
	private Snack snack;
	
	
	
	public Snack getSnack() {
		return snack;
	}
	public void setSnack(Snack snack) {
		this.snack = snack;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	
	
}
