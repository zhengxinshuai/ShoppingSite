package com.dfbb.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dfbb.common.CommentState;
import com.dfbb.common.OrderState;

public class Order {
	private int oid;//订单id
	private int uid;//下单的uid
	private Date createTime;//创建的时间
	private String orderState;//订单的状态
	private String commentState;//评论的状态
	private double shouldPayMoney;//原价
	private double payMoney;//实际支付
	private int addressId;//下单的地址
	private Date deliverTime;//发货时间
	private Date checkTime;//收货时间
	private int serialnum;//订单编号
	
	private List<CartItem> cartItems;//该订单中的商品
	private Address address;//有了这个，获取订单的时候代替下面3个属性
	private String addressStr;//地址字符串信息
	private String contactPerson;//联系人名字
	private String phoneNum;//手机号码
	private String isEvaluate;//被评价过了吗
	private List<OrderItem> orderItems;//得到订单时  使用的是这个..
	private String isShow;
	
	
	
	
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getIsEvaluate() {
		return isEvaluate;
	}
	public void setIsEvaluate(String isEvaluate) {
		this.isEvaluate = isEvaluate;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getCommentState() {
		return commentState;
	}
	public void setCommentState(String commentState) {
		this.commentState = commentState;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getAddressStr() {
		return addressStr;
	}
	public void setAddressStr(String addressStr) {
		this.addressStr = addressStr;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public double getShouldPayMoney() {
		return shouldPayMoney;
	}
	public void setShouldPayMoney(double shouldPayMoney) {
		this.shouldPayMoney = shouldPayMoney;
	}
	public double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public Date getDeliverTime() {
		
		
		
		return deliverTime;
	}
	public void setDeliverTime(Date deliverTime) {
		
		this.deliverTime = deliverTime;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public int getSerialnum() {
		return serialnum;
	}
	public void setSerialnum(int serialnum) {
		this.serialnum = serialnum;
	}
	
	
	
	
	
}
