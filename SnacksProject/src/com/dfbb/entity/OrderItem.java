package com.dfbb.entity;

public class OrderItem {
	private int oid;
	private int sid;
	private int quantity;
	private double itemCount;
	
	private Snack snack;

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
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

	public double getItemCount() {
		return itemCount;
	}

	public void setItemCount(double itemCount) {
		this.itemCount = itemCount;
	}

	public Snack getSnack() {
		return snack;
	}

	public void setSnack(Snack snack) {
		this.snack = snack;
	}
	
	
	
	
}
