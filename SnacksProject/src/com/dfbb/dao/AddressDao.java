package com.dfbb.dao;

import java.sql.Connection;
import java.util.List;

import com.dfbb.entity.Address;
import com.dfbb.entity.Areas;
import com.dfbb.entity.City;
import com.dfbb.entity.Province;



public interface AddressDao {
	List<Province> getAllProvince(Connection connection);
	List<City> getCityByPId(Connection connection,int PId);
	List<Areas> getAreasByCityId(Connection connection,int CId);
	
	int addAddress(Connection connection,Address address);
	
	List<Address> getAddressesByUid(Connection connection,int uid);
	
	String getPNameByPid(Connection connection,int pid);
	String getCNameByCid(Connection connection,int cid);
	String getANameByAid(Connection connection,int aid);
	int deleteAddressByAddressId(Connection connection,int addressId);
	int getTheUserMaxAddressId(Connection connection,int uid);
	/**
	 * 把一个用户地址的默认为Y
	 * @param connection
	 * @param uid
	 * @param addressId
	 * @return
	 */
	int setAddressDefault(Connection connection,int addressId);
	/**
	 * 把一个用户其他的地址设置为N
	 * @param connection
	 * @param addressId
	 * @param userId
	 * @return
	 */
	int setOtherAddressDefaultN(Connection connection,int addressId,int userId);
	/**
	 * 根据addressId得到一条address
	 * @param connection
	 * @param addressId
	 * @return
	 */
	Address getAddressByAddressId(Connection connection,int addressId);
	
	int changeAddressToInVisible(Connection connection,int addressId);
	
}
