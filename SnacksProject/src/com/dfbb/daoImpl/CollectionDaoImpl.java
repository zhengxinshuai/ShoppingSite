package com.dfbb.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dfbb.dao.CollectionDao;
import com.dfbb.dao.SnackDao;
import com.dfbb.entity.CollectionItem;
import com.dfbb.entity.Snack;
import com.dfbb.tool.DataSourceTool;

public class CollectionDaoImpl implements CollectionDao{

	/**
	 * 得到这个账号下所有的收藏物品
	 */
	public List<CollectionItem> getAllCollectionItemsByUid(Connection connection, int uid) {
		String sql="select * from collection where uid=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<CollectionItem> collectionItems=new ArrayList<CollectionItem>();
		
		try {
			ps=connection.prepareStatement(sql);
			ps.setInt(1, uid);
			rs=ps.executeQuery();
			SnackDao snackDao=new SnackDaoImpl();
			while (rs.next()) {
				CollectionItem collectionItem=new CollectionItem();
				int sid= rs.getInt("sid");
				int uid1=rs.getInt("uid");
				collectionItem.setSid(sid);
				collectionItem.setUid(uid1);
				Snack snack= snackDao.getSnackBySid(connection, sid);
				collectionItem.setSnack(snack);
				collectionItems.add(collectionItem);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return collectionItems;
	}

	@Override
	public int deleteCollectionByUidASid(Connection connection, int uid, int sid) {
		String sql="delete from collection where sid=? and uid=?";
		Object[] params={sid,uid};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		
		return res;
	}

	@Override
	public CollectionItem getCollectionItemBySidAUid(Connection connection, int uid, int sid) {
		String sql = "select * from collection where uid=? and sid=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		CollectionItem collectionItem=null;

		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, uid);
			ps.setInt(2, sid);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				collectionItem = new CollectionItem();
				int sid1 = rs.getInt("sid");
				int uid1 = rs.getInt("uid");
				collectionItem.setSid(sid1);
				collectionItem.setUid(uid1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return collectionItem;
	}

	@Override
	public int addCollection(Connection connection, int uid, int sid) {
		String sql="insert into collection (`sid`,`uid`) values (?,?)";
		Object[] params={sid,uid};
		int res=DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}
	
}
