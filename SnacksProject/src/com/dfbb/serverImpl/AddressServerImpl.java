package com.dfbb.serverImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.dfbb.dao.AddressDao;
import com.dfbb.daoImpl.AddressDaoImpl;
import com.dfbb.entity.Address;
import com.dfbb.entity.Areas;
import com.dfbb.entity.City;
import com.dfbb.entity.Province;
import com.dfbb.server.AddressServer;
import com.dfbb.tool.DataSourceTool;

public class AddressServerImpl implements AddressServer{
	AddressDao addressDao=new AddressDaoImpl();
	
	/**
	 * 得到所有的省
	 */
	public List<Province> getAllProvinces() {
		Connection connection= DataSourceTool.getConnection();
		List<Province> provinces= addressDao.getAllProvince(connection);
		
		DataSourceTool.closeAll(connection, null, null);
		return provinces;
	}

	public List<City> getCitiesByPId(int PId) {
		Connection connection= DataSourceTool.getConnection();
		List<City> cities= addressDao.getCityByPId(connection, PId);
		DataSourceTool.closeAll(connection, null, null);
		return cities;
	}

	public List<Areas> getAreasByCId(int CiD) {
		Connection connection= DataSourceTool.getConnection();
		List<Areas> areas= addressDao.getAreasByCityId(connection, CiD);
		DataSourceTool.closeAll(connection, null, null);
		return areas;
	}

	@Override
	public int addAddress(Address address) {
		Connection connection= DataSourceTool.getConnection();
		int res= addressDao.addAddress(connection, address);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}

	/**
	 * 通过uid得到地址的集合
	 */
	public List<Address> getAddressesByUid(int uid) {
		Connection connection= DataSourceTool.getConnection();
		List<Address> addresses= addressDao.getAddressesByUid(connection, uid);
		for (int i = 0; i < addresses.size(); i++) {
			Address address= addresses.get(i);
			int provinceId= address.getProvinceid();
			int cityId= address.getCityid();
			int areaId= address.getAreaid();
			String pName = addressDao.getPNameByPid(connection, provinceId);
			String cName = addressDao.getCNameByCid(connection, cityId);
			String aName = addressDao.getANameByAid(connection, areaId);
			address.setProvinceName(pName);
			address.setCityName(cName);
			address.setAreaName(aName);	
		}
		DataSourceTool.closeAll(connection, null, null);
		return addresses;
	}

	@Override
	public int deleteAddressByAddressId(int addressId) {
		Connection connection= DataSourceTool.getConnection();
		int res= addressDao.deleteAddressByAddressId(connection, addressId);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}

	@Override
	public int getTheUserMaxAddressId(int uid) {
		Connection connection= DataSourceTool.getConnection();
		int res= addressDao.getTheUserMaxAddressId(connection, uid);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}

	
	public boolean setAddressDefault(int addressId, int uid) {
		Connection connection= DataSourceTool.getConnection();
		try {
			connection.setAutoCommit(false);
			int res1= addressDao.setAddressDefault(connection, addressId);
			int res2=addressDao.setOtherAddressDefaultN(connection, addressId, uid);
			if (res1>0&&res2>0) {
				connection.commit();
				return true;
			}else {
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			return false;
		}finally {
			DataSourceTool.closeAll(connection, null, null);
		}
	}

	@Override
	public Address getAddressByAddressId(int addressId) {
		Connection connection=DataSourceTool.getConnection();
		Address address= addressDao.getAddressByAddressId(connection, addressId);
		int pid= address.getProvinceid();
		int aid= address.getAreaid();
		int cid= address.getCityid();
		String pName= addressDao.getPNameByPid(connection, pid);
		String cName= addressDao.getCNameByCid(connection, cid);
		String aName=addressDao.getANameByAid(connection, aid);
		address.setProvinceName(pName);
		address.setCityName(cName);
		address.setAreaName(aName);
		address.setWholeAddressStr(pName+" "+cName+" "+aName);
		DataSourceTool.closeAll(connection, null, null);
		
		
		return address;
	}

	@Override
	public int changeAddressToInVisible(int addressId) {
		Connection connection= DataSourceTool.getConnection();
		int res= addressDao.changeAddressToInVisible(connection, addressId);
		DataSourceTool.closeAll(connection, null, null);
		return res;
	}

	

	
}
