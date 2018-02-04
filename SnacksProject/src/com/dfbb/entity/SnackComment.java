package com.dfbb.entity;

import java.io.Serializable;
import java.util.Date;



import com.dfbb.serverImpl.UserServerImpl;

import com.dfbb.server.UserServer;

public class SnackComment implements Serializable{
	private int sid;
	private int uid;
	private Date date=new Date();
	private String comment;
	
	private User user;//该评论对应的user
	
	
	
	
	public User getUser() {
		return user;
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
	public void setUid(int uid) {//在设置uid的时候，同时设定user
		UserServer userServer=new UserServerImpl();
		user= userServer.getUserById(uid);
		this.uid = uid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String toString(){
		
		return "sid:"+sid+",uid:"+uid+",Date:"+date+",comment："+comment;
		
		
	}
	
	
}
