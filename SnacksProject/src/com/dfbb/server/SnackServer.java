package com.dfbb.server;

import java.util.List;

import com.dfbb.common.OrderByWhat;
import com.dfbb.entity.Page;
import com.dfbb.entity.Snack;
import com.dfbb.entity.SnackComment;
import com.dfbb.entity.SnackPicture;

public interface SnackServer {
	/**
	 * 查询零食的方法。
	 * @param info
	 * @param page
	 * @return
	 */
	List<Snack> querySnack(Page page,OrderByWhat orderByWhat,String info);
	
	List<SnackPicture> getSnackPicturesBySid(int sid);
	
	Snack getSnackBySid(int sid);
	
	List<SnackComment> getSnackCommentsBySid(int sid); 
	
	int getSnackCountByKeyWord(String keyword);
	
	double getSinglePriceBySid(int sid);
	
	
}
