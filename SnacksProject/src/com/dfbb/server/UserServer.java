package com.dfbb.server;

import com.dfbb.entity.Address;
import com.dfbb.entity.PersonAttrs;
import com.dfbb.entity.User;

public interface UserServer {
	int register(User user,Address address);
	User checkUName(String uName);
	User userLogin(User user);
	User getUserById(int id);
	
	String changePwd(String oldPwd,String newPwd,int uid);
	/**
	 * 修改个人信息 
	 * @param personAttrs
	 * @return 
	 */
	int changePersonDetals(PersonAttrs personAttrs);
	
	User getUserByUserName(String userName);
	
	User getUserByPwd(String pwd,int uid);
	/**
	 * 修改头像，并返回旧头像的位置
	 * @param uid
	 * @param iconPath
	 * @return
	 */
	int updateIconPath(int uid,String iconPath);
	
	String getUserIconPath(int uid);
	
	User managerLogin(String uName,String pwd);
}
