package com.dfbb.dao;

import com.dfbb.common.OrderByWhat;
import com.dfbb.entity.Page;
import com.dfbb.entity.Snack;
import com.dfbb.entity.SnackComment;
import com.dfbb.entity.SnackPicture;

import java.sql.Connection;
import java.util.List;

public interface SnackDao {
	//根据你要的数量和指定的排序，获得指定大小的零食集合
	List<Snack> getSnacksList(Connection connection,Page page,OrderByWhat orderByWhat,String info);

	//得到一个零食的所有图片路径
	List<SnackPicture> getPathsBySid(Connection connection,int sid);
	
	Snack getSnackBySid(Connection connection,int sid);
	
	List<SnackComment> getSnackCommentsBySid(Connection connection,int sid);
	
	int getSnacksCountByKeyword(Connection connection,String keyword);
	
	//根据sid得到新的价格
	double getSinglePriceBySid(Connection connection,int sid);
	/**
	 * 根据sid得到对应零食的库存
	 * @param connection
	 * @param sid
	 * @return
	 */
	int getStocksBySid(Connection connection,int sid);
	
	/**
	 * 将sid下的库存设置为stocks
	 * @param connection
	 * @param stocks
	 * @param sid
	 * @return
	 */
	int updateStocks(Connection connection,int stocks,int sid);
}
