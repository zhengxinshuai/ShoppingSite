package com.dfbb.dao;

import java.sql.Connection;

import com.dfbb.entity.PersonAttrs;
import com.dfbb.entity.User;

public interface UserDao {
	int addUser(Connection connection,User user);
	int getLastUserId(Connection connection);
	int getUserIdByUserName(Connection connection,String userName);
	User getUserByUName(Connection connection,String uname);
	
	User getUserByUnameAndPwd(Connection connection,User user);
	User getUserById(Connection connection,int id);
	/**
	 * 根据uid找到对应用户的余额
	 * @param connection
	 * @param uid
	 * @return
	 */
	double getUserMoneyLeft(Connection connection,int uid);
	
	int updateMoney(Connection connection,int uid,double money);
	/**
	 * 根据一个用户的id和密码得到一个User(可以用来验证密码是否正确)
	 * @param connection
	 * @param pwd
	 * @param uid
	 * @return
	 */
	User getUserByPwd(Connection connection,String pwd,int uid);
	
	int updatePwd(Connection connection,String pwd,int uid);
	
	int changePersonDetals(Connection connection,PersonAttrs personAttrs);
	
	int updateUserIconPath(Connection connection,int uid,String iconPath);
	
	String getIconPathByUid(Connection connection,int uid);
	/**
	 * 管理员通过账号和密码得到User对象
	 * @return
	 */
	User managerGetUserByUAP(Connection connection,String uName,String pwd);
}
