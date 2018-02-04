package com.dfbb.serverImpl;

import java.sql.Connection;
import java.util.List;



import com.dfbb.dao.CollectionDao;
import com.dfbb.daoImpl.CollectionDaoImpl;
import com.dfbb.entity.CollectionItem;
import com.dfbb.server.CollectionServer;
import com.dfbb.tool.DataSourceTool;



public class CollectionServerImpl implements CollectionServer{
	CollectionDao collectionDao=new CollectionDaoImpl();
	
	public List<CollectionItem> getCollectionItemsByUid(int uid) {
		Connection connection= DataSourceTool.getConnection();
		List<CollectionItem> collectionItems= collectionDao.getAllCollectionItemsByUid(connection, uid);
		DataSourceTool.closeAll(connection, null, null);
		return collectionItems;
	}

	@Override
	public int deleteCollectionByUidASid(int uid, int sid) {
		Connection connection= DataSourceTool.getConnection();
		int res= collectionDao.deleteCollectionByUidASid(connection, uid, sid);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}

	/**
	 * 得到一条CollectionItem
	 */
	public CollectionItem getCollectionItemByUidASid(int uid, int sid) {
		Connection connection= DataSourceTool.getConnection();
		CollectionItem collectionItem= collectionDao.getCollectionItemBySidAUid(connection, uid, sid);
		DataSourceTool.closeAll(connection, null, null);
		return collectionItem;
	}

	@Override
	public int addCollection(int uid, int sid) {
		Connection connection= DataSourceTool.getConnection();
		int res= collectionDao.addCollection(connection, uid, sid);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}
	
}
