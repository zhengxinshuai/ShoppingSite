package com.dfbb.server;

import java.sql.Connection;
import java.util.List;

import com.dfbb.entity.CollectionItem;
import com.dfbb.tool.DataSourceTool;

public interface CollectionServer {
	List<CollectionItem> getCollectionItemsByUid(int uid);
	
	int deleteCollectionByUidASid( int uid, int sid);
	CollectionItem getCollectionItemByUidASid(int uid,int sid);
	int addCollection(int uid,int sid);
}
