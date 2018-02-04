package com.dfbb.serverImpl;

import java.sql.Connection;
import java.util.List;


import com.dfbb.common.OrderByWhat;
import com.dfbb.dao.SnackDao;
import com.dfbb.daoImpl.SnackDaoImpl;
import com.dfbb.entity.Page;
import com.dfbb.entity.Snack;
import com.dfbb.entity.SnackComment;
import com.dfbb.entity.SnackPicture;
import com.dfbb.server.SnackServer;
import com.dfbb.tool.DataSourceTool;

public class SnackServerImpl implements SnackServer{
	
	SnackDao snackDao=new SnackDaoImpl();
	
	@Override
	public List<Snack> querySnack(Page page,OrderByWhat orderByWhat,String info) {
		Connection connection= DataSourceTool.getConnection();
		List<Snack> snacks= snackDao.getSnacksList(connection, page, orderByWhat, info);
		DataSourceTool.closeAll(connection, null, null);
		return snacks;
	}

	/**
	 * 根据id得到路径
	 */
	public List<SnackPicture> getSnackPicturesBySid(int sid) {
		Connection connection= DataSourceTool.getConnection();
		List<SnackPicture> snackPictures= snackDao.getPathsBySid(connection, sid);
		DataSourceTool.closeAll(connection, null, null);
		return snackPictures;
	}

	/**
	 * 通过sid得到零食
	 */
	public Snack getSnackBySid(int sid) {
		Connection connection= DataSourceTool.getConnection();
		Snack snack= snackDao.getSnackBySid(connection, sid);
		DataSourceTool.closeAll(connection, null, null);
		return snack;
	}

	@Override
	public List<SnackComment> getSnackCommentsBySid(int sid) {
		Connection connection= DataSourceTool.getConnection();
		List<SnackComment> snackComments= snackDao.getSnackCommentsBySid(connection, sid);
		DataSourceTool.closeAll(connection, null, null);
		return snackComments;
	}

	@Override
	public int getSnackCountByKeyWord(String keyword) {
		Connection connection= DataSourceTool.getConnection();
		int count= snackDao.getSnacksCountByKeyword(connection, keyword);
		DataSourceTool.closeAll(connection, null, null);
		return count;
	}

	/**
	 * 根据sid得到单价
	 */
	public double getSinglePriceBySid(int sid) {
		Connection connection= DataSourceTool.getConnection();
		double price= snackDao.getSinglePriceBySid(connection, sid);
		DataSourceTool.closeAll(connection, null, null);
		return price;
	}



}
