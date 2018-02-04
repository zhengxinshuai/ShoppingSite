package com.dfbb.tool;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceTool {

	private static DataSource ds;
	
	static{
		initDataSource();
	}
	
	
	
	//初始化数据源
	public static void initDataSource(){
		try {
			Context ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/snacks");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConnection(){

		Connection connection=null;
		try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
		
	}
	
	public static void closeAll(Connection connection,PreparedStatement ps,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 增删改通用方法
	 * @param sql
	 * @param prarms
	 * @return
	 */
	public static int exceuteUpdate(Connection connection,String sql,Object[] prarms){
		PreparedStatement ps=null;
		int num=0;
		try {
			ps=connection.prepareStatement(sql);
			if(prarms.length!=0){
				for (int i = 0; i < prarms.length; i++) {
					ps.setObject(i+1, prarms[i]);
				}
			}
			num=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeAll(null, ps, null);
		}
		
		return num;
	}
	
	public static String processStr(String str){
		StringBuffer sb=new StringBuffer(str);
		int length= str.length();
		for (int i = 0; i < str.length(); i++) {
			if (i<length-1) {
				sb.insert(i+i+1, '%');
			}
			
		}
		
		return sb.toString();
	}
	
	
}
