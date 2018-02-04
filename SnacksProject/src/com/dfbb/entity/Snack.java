package com.dfbb.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 零食实体类
 * @author apple
 *
 */
public class Snack implements Serializable{
	private int sid;//零食id
	private String sname;//零食名称
	private double pricesold;//
	private double pricenew;
	private int stocks;
	private String comment;
	private int praise;
	private Date date;
	private int sellQuntity;
	private String iconPath;
	
	
	
	
	public double getPricesold() {
		return pricesold;
	}
	public void setPricesold(double pricesold) {
		this.pricesold = pricesold;
	}
	public double getPricenew() {
		return pricenew;
	}
	public void setPricenew(double pricenew) {
		this.pricenew = pricenew;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getSellQuntity() {
		return sellQuntity;
	}
	public void setSellQuntity(int sellQuntity) {
		this.sellQuntity = sellQuntity;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getStocks() {
		return stocks;
	}
	public void setStocks(int stocks) {
		this.stocks = stocks;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getPraise() {
		return praise;
	}
	public void setPraise(int praise) {
		this.praise = praise;
	}
	
	
}
