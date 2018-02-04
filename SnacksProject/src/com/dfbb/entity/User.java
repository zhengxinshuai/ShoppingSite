package com.dfbb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class User implements Serializable,HttpSessionBindingListener{
	
	private int id;
	private String userName;
	private String password;
	private double money;
	private String realName;
	private String iconPath;
	private Date birthday;
	private String phoneNum;
	private String isManager;
	private String nickName;
	
	
	
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getIsManager() {
		return isManager;
	}
	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}
	private String sex;

	public User(){}
	
	public User(String userName,String password){
		this.userName=userName;
		this.password=password;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		HttpSession session= event.getSession();
		ServletContext application= session.getServletContext();
		HashMap<String, HttpSession> userMap= (HashMap<String, HttpSession>)application.getAttribute("users");
		if (userMap==null) {//如果userMap为空的话
			userMap=new HashMap<String, HttpSession>();
			
		}
		userMap.put(this.userName, session);
		application.setAttribute("users", userMap);//设置回去
		
		
		
	}
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		HttpSession session= event.getSession();
		ServletContext application= session.getServletContext();
		HashMap<String, HttpSession> userMap= (HashMap<String, HttpSession>)application.getAttribute("users");
		if (userMap==null) {
			return;
		}
		userMap.remove(this.userName);
		application.setAttribute("users", userMap);//设置回去
	}
	
	
}
