package com.dfbb.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dfbb.common.OrderByWhat;
import com.dfbb.dao.SnackDao;
import com.dfbb.dao.UserDao;
import com.dfbb.entity.Page;
import com.dfbb.entity.Snack;
import com.dfbb.entity.SnackComment;
import com.dfbb.entity.SnackPicture;
import com.dfbb.entity.User;
import com.dfbb.tool.DataSourceTool;


public class SnackDaoImpl implements SnackDao{

	/**
	 * 根据查询条件查询的通用方法
	 */
	public List<Snack> getSnacksList(Connection connection, Page page, OrderByWhat orderByWhat,String info) {
		String sql=null;
		int perPageCount= page.getPerPageCount();//得到每一页的数量
		int index=page.getStartIndex();//得到开始的下标
		switch (orderByWhat) {
		case PriceAsc:
			sql="select * from snacks where sname like ? order by pricesnew asc limit ?,?";
			break;
		case PriceDesc:
			sql="select * from snacks where sname like ? order by pricesnew desc limit ?,?";
			break;
		case PraiseAsc:
			sql="select * from snacks where sname like ? order by praise asc limit ?,?";
			break;
		case PraiseDesc:
			sql="select * from snacks where sname like ? order by praise desc limit ?,?";
			break;
		case DateAsc:
			sql="select * from snacks where sname like ? order by date asc limit ?,?";
			break;
		case DateDesc:
			sql="select * from snacks where sname like ? order by date desc limit ?,?";
			break;
		case SellQuntityAsc:
			sql="select * from snacks where sname like ? order by sellQuntity asc limit ?,?";
			break;
		case SellQuntityDesc:
			sql="select * from snacks where sname like ? order by sellQuntity desc limit ?,?";
			break;
		default:
			break;
		}
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Snack> snacks=new ArrayList<Snack>();
		try {
			ps= connection.prepareStatement(sql);
			info= DataSourceTool.processStr(info);
			ps.setString(1, "%"+info+"%");//设置查询的名称
			ps.setInt(2, index);
			ps.setInt(3, perPageCount);
			rs=ps.executeQuery();
			while (rs.next()) {
				Snack snack=new Snack();
				int sid= rs.getInt("sid");
				String sname = rs.getString("sname");
				double pricesold= rs.getDouble("pricesold");
				double priceNew= rs.getDouble("pricesnew");
				int stocks= rs.getInt("stocks");
				String comment= rs.getString("comment");
				int praise= rs.getInt("praise");//获取点赞数
				Date date= rs.getTimestamp("date");
				int sellQuntity= rs.getInt("sellQuntity");
				String iconpath = rs.getString("iconpath");
				
				
				snack.setSid(sid);
				snack.setSname(sname);
				snack.setPricesold(pricesold);
				snack.setPricenew(priceNew);
				snack.setStocks(stocks);
				snack.setComment(comment);
				snack.setPraise(praise);
				snack.setDate(date);
				snack.setSellQuntity(sellQuntity);
				snack.setIconPath(iconpath);
				snacks.add(snack);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		
		return snacks;
	}


	public List<SnackPicture> getPathsBySid(Connection connection, int sid) {
		String sql="select * from snackspicture where sid=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<SnackPicture> snackPictures=new ArrayList<SnackPicture>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, sid);
			rs= ps.executeQuery();
			while (rs.next()) {
				SnackPicture snackPicture=new SnackPicture();
				int sid1= rs.getInt("sid");
				String path= rs.getString("picturePath");
				String isMain= rs.getString("isMain");
				String isIcon = rs.getString("isIcon");
				snackPicture.setSid(sid1);
				snackPicture.setPicturePath(path);
				snackPicture.setIsMain(isMain);
				snackPicture.setIsIcon(isIcon);
				
				snackPictures.add(snackPicture);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return snackPictures;
	}
	/**
	 * 根据零食的sid来得到这个零食所有的信息
	 */
	public Snack getSnackBySid(Connection connection, int sid) {
		String sql="select * from snacks where sid=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		Snack snack=null;
		try {
			ps= connection.prepareStatement(sql);
			ps.setInt(1, sid);
			rs=ps.executeQuery();
			while (rs.next()) {
				snack=new Snack();
				int sid1= rs.getInt("sid");
				String sname = rs.getString("sname");
				double pricesold= rs.getDouble("pricesold");
				double priceNew= rs.getDouble("pricesnew");
				int stocks= rs.getInt("stocks");
				String comment= rs.getString("comment");
				int praise= rs.getInt("praise");
				Date date= rs.getDate("date");
				int sellQuntity= rs.getInt("sellQuntity");
				String iconpath = rs.getString("iconpath");
					
				snack.setSid(sid1);
				snack.setSname(sname);
				snack.setPricesold(pricesold);
				snack.setPricenew(priceNew);
				snack.setStocks(stocks);
				snack.setComment(comment);
				snack.setPraise(praise);
				snack.setDate(date);
				snack.setSellQuntity(sellQuntity);
				snack.setIconPath(iconpath);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return snack;
	}


	@Override
	public List<SnackComment> getSnackCommentsBySid(Connection connection, int sid) {
		String sql="select * from snackscomment where sid=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		SnackComment snackComment=null;
		List<SnackComment> snackComments=new ArrayList<SnackComment>();

		try {
			ps= connection.prepareStatement(sql);
			ps.setInt(1, sid);
			rs=ps.executeQuery();
			while (rs.next()) {
				snackComment=new SnackComment();
				int sid1= rs.getInt("sid");
				int uid= rs.getInt("uid");
				String comment= rs.getString("comment");
				Date date= rs.getTimestamp("dateTime");
				
					
				snackComment.setSid(sid1);
				snackComment.setUid(uid);
				snackComment.setComment(comment);
				snackComment.setDate(date);
				
				snackComments.add(snackComment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return snackComments;
	}


	@Override
	public int getSnacksCountByKeyword(Connection connection, String keyword) {
		String sql="select count(1) from snacks where sname like ?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		int count=-1;
		try {
			keyword= DataSourceTool.processStr(keyword);
			
			
			ps = connection.prepareStatement(sql);
			ps.setString(1, "%"+keyword+"%");//设置查询的名称
			rs= ps.executeQuery();
			if (rs.next()) {
				count=rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return count;
	}


	/**
	 * 根据sid得到单价
	 */
	public double getSinglePriceBySid(Connection connection, int sid) {
		String sql="select pricesnew from snacks where sid = ?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		double singlePrice=-1;
		try {
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, sid);
			rs= ps.executeQuery();
			if (rs.next()) {
				singlePrice=rs.getDouble("pricesnew");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return singlePrice;
	}


	@Override
	public int getStocksBySid(Connection connection, int sid) {
		String sql="select stocks from snacks where sid=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		int stocks=0;
		try {
			ps= connection.prepareStatement(sql);
			ps.setInt(1, sid);
			rs= ps.executeQuery();
			if (rs.next()) {
				stocks= rs.getInt("stocks");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return stocks;
	}


	@Override
	public int updateStocks(Connection connection, int stocks, int sid) {
		String sql="update snacks set stocks=? where sid=?";
		Object[] params={stocks,sid};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}


}
