package com.dfbb.serverImpl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.internet.NewsAddress;

import com.dfbb.dao.AddressDao;
import com.dfbb.dao.UserDao;
import com.dfbb.daoImpl.AddressDaoImpl;
import com.dfbb.daoImpl.UserDaoImpl;
import com.dfbb.entity.Address;
import com.dfbb.entity.PersonAttrs;
import com.dfbb.entity.User;
import com.dfbb.server.UserServer;
import com.dfbb.tool.DataSourceTool;

public class UserServerImpl implements UserServer{
	UserDao userDao=new UserDaoImpl();
	AddressDao addressDao=new AddressDaoImpl();
	@Override
	public int register(User user,Address address) {
		Connection connection= DataSourceTool.getConnection();
		try {
			int affectRow1=-1;
			int affectRow2=-1;
			connection.setAutoCommit(false);
			//先往用户数据库里加入一个用户
			affectRow1=userDao.addUser(connection, user);
			int nowid=userDao.getUserIdByUserName(connection, user.getUserName());
			
			address.setUserId(nowid);
			affectRow2= addressDao.addAddress(connection, address);
			if (affectRow1>0&&affectRow2>0) {
				connection.commit();
				return 1;
			}else {
				connection.rollback();
				return -1;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(connection, null, null);
		}
		
		return -1;
	}
	@Override
	public User checkUName(String uName) {
		Connection connection= DataSourceTool.getConnection();
		User user= userDao.getUserByUName(connection, uName);
		DataSourceTool.closeAll(connection, null, null);
		return user;
	}
	@Override
	public User userLogin(User user) {
		Connection connection= DataSourceTool.getConnection();
		User user1= userDao.getUserByUnameAndPwd(connection, user);
		DataSourceTool.closeAll(connection, null, null);
		return user1;
	}
	@Override
	public User getUserById(int id) {
		Connection connection= DataSourceTool.getConnection();
		User user1= userDao.getUserById(connection, id);
		DataSourceTool.closeAll(connection, null, null);
		return user1;
	}
	@Override
	public String changePwd(String oldPwd, String newPwd, int uid) {
		Connection connection= DataSourceTool.getConnection();
		//先判断密码是否正确
		User user= userDao.getUserByPwd(connection, oldPwd, uid);
		String info="";
		if (user==null) {
			//密码不正确
			info= "原密码不正确,修改失败";
		}else {
			//密码正确 修改密码
			int res=userDao.updatePwd(connection, newPwd, uid);
			if (res>0) {
				info= "密码修改成功!";
			}else {
				info= "密码修改失败!";
			}
	
		}
		DataSourceTool.closeAll(connection, null, null);
		return info ;
	}
	@Override
	public int changePersonDetals(PersonAttrs personAttrs) {
		Connection connection= DataSourceTool.getConnection();
		int res= userDao.changePersonDetals(connection, personAttrs);
		DataSourceTool.closeAll(connection, null, null);
		
		return res;
	}
	@Override
	public User getUserByUserName(String userName) {
		Connection connection= DataSourceTool.getConnection();
		User user= userDao.getUserByUName(connection, userName);
		DataSourceTool.closeAll(connection, null, null);
		
		return user;
	}
	@Override
	public User getUserByPwd(String pwd, int uid) {
		Connection connection= DataSourceTool.getConnection();
		User user= userDao.getUserByPwd(connection, pwd, uid);
		DataSourceTool.closeAll(connection, null, null);
		return user;
	}
	@Override
	public int updateIconPath(int uid, String iconPath) {
		Connection connection= DataSourceTool.getConnection();
		
		int res= userDao.updateUserIconPath(connection, uid, iconPath);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}
	@Override
	public String getUserIconPath(int uid) {
		Connection connection = DataSourceTool.getConnection();

		String res = userDao.getIconPathByUid(connection, uid);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}
	@Override
	public User managerLogin(String uName, String pwd) {
		Connection connection=DataSourceTool.getConnection();
		User user=userDao.managerGetUserByUAP(connection, uName, pwd);
		DataSourceTool.closeAll(connection, null, null);
		
		return user;
	}



}
