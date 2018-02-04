package com.dfbb.dao;

import java.sql.Connection;
import java.util.List;

import com.dfbb.entity.CollectionItem;

public interface CollectionDao {
	List<CollectionItem> getAllCollectionItemsByUid(Connection connection,int uid);
	/**
	 * 根据uid和sid删除掉一条信息
	 * @param connection
	 * @param uid
	 * @param sid
	 * @return
	 */
	int deleteCollectionByUidASid(Connection connection,int uid,int sid);
	CollectionItem getCollectionItemBySidAUid(Connection connection,int uid,int sid);
	int addCollection(Connection connection,int uid,int sid);
}
