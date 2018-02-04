package com.dfbb.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.dfbb.dao.UserDao;
import com.dfbb.entity.PersonAttrs;
import com.dfbb.entity.User;
import com.dfbb.tool.DataSourceTool;

public class UserDaoImpl implements UserDao{
	
	
	public int addUser(java.sql.Connection connection, com.dfbb.entity.User user) {
		String sql="Insert into user (`userName`,`password`,`money`,`realname`,`iconpath`,`birthday`,`phonenum`,`isManager`,`sex`) values(?,?,?,?,?,?,?,?,?)";
		Object[] params={user.getUserName(),user.getPassword(),user.getMoney(),user.getRealName(),user.getIconPath(),user.getBirthday(),user.getPhoneNum(),user.getIsManager(),user.getSex()};
		int affectRow=DataSourceTool.exceuteUpdate(connection, sql, params);
		return affectRow;
	}

	@Override
	/**
	 * 得到当前最大的UserId
	 */
	public int getLastUserId(Connection connection) {
		String sql="select max(`id`) from user";
		PreparedStatement ps=null;
		ResultSet rs=null;
		int id=0;
		try {
			ps= connection.prepareStatement(sql);
			rs= ps.executeQuery();
			if (rs.next()) {
				id= rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return id;
	}

	@Override
	public int getUserIdByUserName(Connection connection, String userName) {
		String sql="select id from user where userName=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		int id=0;
		try {
			ps= connection.prepareStatement(sql);
			ps.setString(1, userName);
			rs= ps.executeQuery();
			if (rs.next()) {
				id= rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return id;
	}

	@Override
	public User getUserByUName(Connection connection, String uname) {
		String sql="select * from user where userName=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		User user=null;
		try {
			ps= connection.prepareStatement(sql);
			ps.setString(1, uname);
			rs= ps.executeQuery();
			if (rs.next()) {
				int id=rs.getInt("id");
				String userName=rs.getString("userName");
				String pwd= rs.getString("password");
				double money=rs.getDouble("money");
				String realName=rs.getString("realname");
				Date date= rs.getDate("birthday");
				String phonenum= rs.getString("phonenum");
				String isManager= rs.getString("ismanager");
				String sex= rs.getString("sex");
				String nickName = rs.getString("nickname");
				user=new User();
				user.setUserName(userName);
				user.setPassword(pwd);
				user.setId(id);
				user.setMoney(money);
				user.setRealName(realName);
				user.setBirthday(date);
				user.setPhoneNum(phonenum);
				user.setIsManager(isManager);
				user.setSex(sex);
				user.setNickName(nickName);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return user;
	}

	/*
	 * 通过账号密码查找用户
	 */
	@Override
	public User getUserByUnameAndPwd(Connection connection, User user) {
		String sql="select * from user where userName=? and password=? and ismanager='N'";
		PreparedStatement ps=null;
		ResultSet rs=null;
		User user1=null;
		try {
			ps= connection.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassword());
			rs= ps.executeQuery();
			if (rs.next()) {
				int id=rs.getInt("id");
				String userName=rs.getString("userName");
				String pwd= rs.getString("password");
				double money=rs.getDouble("money");
				String realName=rs.getString("realname");
				Date date= rs.getDate("birthday");
				String phonenum= rs.getString("phonenum");
				String isManager= rs.getString("ismanager");
				String sex= rs.getString("sex");
				String iconpath= rs.getString("iconpath");
				String nickName=rs.getString("nickname");
				user1=new User();
				user1.setUserName(userName);
				user1.setPassword(pwd);
				user1.setId(id);
				user1.setMoney(money);
				user1.setRealName(realName);
				user1.setBirthday(date);
				user1.setPhoneNum(phonenum);
				user1.setIsManager(isManager);
				user1.setSex(sex);
				user1.setIconPath(iconpath);
				user1.setNickName(nickName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return user1;
	}

	@Override
	public User getUserById(Connection connection, int id) {
		String sql="select * from user where id=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		User user1=null;
		try {
			ps= connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs= ps.executeQuery();
			if (rs.next()) {
				int id1=rs.getInt("id");
				String userName=rs.getString("userName");
				String pwd= rs.getString("password");
				double money=rs.getDouble("money");
				String realName=rs.getString("realname");
				Date date= rs.getDate("birthday");
				String phonenum= rs.getString("phonenum");
				String isManager= rs.getString("ismanager");
				String sex= rs.getString("sex");
				String iconpath= rs.getString("iconpath");
				String nickName = rs.getString("nickname");
				user1=new User();
				user1.setUserName(userName);
				user1.setPassword(pwd);
				user1.setId(id1);
				user1.setMoney(money);
				user1.setRealName(realName);
				user1.setBirthday(date);
				user1.setPhoneNum(phonenum);
				user1.setIsManager(isManager);
				user1.setSex(sex);
				user1.setIconPath(iconpath);
				user1.setNickName(nickName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return user1;
	}

	@Override
	public double getUserMoneyLeft(Connection connection, int uid) {
		String sql="select money from user where id=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		double money=0;
		try {
			ps= connection.prepareStatement(sql);
			ps.setInt(1, uid);
			rs= ps.executeQuery();
			if (rs.next()) {
				money= rs.getInt("money");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return money;
	}

	@Override
	public int updateMoney(Connection connection, int uid, double money) {
		String sql="update user set money=? where id=?";
		Object[] params={money,uid};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public User getUserByPwd(Connection connection, String pwd, int uid) {
		String sql="select userName from user where id=? and password=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		User user=null;
		try {
			ps= connection.prepareStatement(sql);
			ps.setInt(1, uid);
			ps.setString(2, pwd);
			rs= ps.executeQuery();
			if (rs.next()) {
				user= new User();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return user;
	}

	@Override
	public int updatePwd(Connection connection, String pwd, int uid) {
		String sql="update user set password=? where id=?";
		Object[] params={pwd,uid};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public int changePersonDetals(Connection connection, PersonAttrs personAttrs) {
		String sql="update user set realname=?,nickname=?,sex=? where id=?";
		Object[] params={personAttrs.getRealName(),personAttrs.getNickName(),personAttrs.getSex(),personAttrs.getUid()};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public int updateUserIconPath(Connection connection, int uid, String iconPath) {
		String sql="update user set iconpath=? where id=?";
		Object[] params={iconPath,uid};
		int res = DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public String getIconPathByUid(Connection connection, int uid) {
		String sql="select iconpath from user where id=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		String iconPath=null;
		try {
			ps= connection.prepareStatement(sql);
			ps.setInt(1, uid);
			
			rs= ps.executeQuery();
			if (rs.next()) {
				iconPath= rs.getString("iconpath");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return iconPath;
	}

	@Override
	public User managerGetUserByUAP(Connection connection, String uName, String pwd) {
		String sql="select * from user where userName=? and password=? and isManager='Y'";
		PreparedStatement ps=null;
		ResultSet rs=null;
		User user1=null;
		try {
			ps= connection.prepareStatement(sql);
			ps.setString(1, uName);
			ps.setString(2, pwd);
			rs= ps.executeQuery();
			if (rs.next()) {
				int id1=rs.getInt("id");
				String userName=rs.getString("userName");
				String pwd1= rs.getString("password");
				double money=rs.getDouble("money");
				String realName=rs.getString("realname");
				Date date= rs.getDate("birthday");
				String phonenum= rs.getString("phonenum");
				String isManager= rs.getString("ismanager");
				String sex= rs.getString("sex");
				String iconpath= rs.getString("iconpath");
				String nickName = rs.getString("nickname");
				user1=new User();
				user1.setUserName(userName);
				user1.setPassword(pwd1);
				user1.setId(id1);
				user1.setMoney(money);
				user1.setRealName(realName);
				user1.setBirthday(date);
				user1.setPhoneNum(phonenum);
				user1.setIsManager(isManager);
				user1.setSex(sex);
				user1.setIconPath(iconpath);
				user1.setNickName(nickName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return user1;
	}

	
}
