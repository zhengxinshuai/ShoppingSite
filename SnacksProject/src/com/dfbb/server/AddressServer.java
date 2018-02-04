package com.dfbb.server;

import java.sql.Connection;
import java.util.List;

import com.dfbb.entity.Address;
import com.dfbb.entity.Areas;
import com.dfbb.entity.City;
import com.dfbb.entity.Province;


public interface AddressServer {
	List<Province> getAllProvinces();
	List<City> getCitiesByPId(int PiD);
	List<Areas> getAreasByCId(int CiD);
	int addAddress(Address address);
	//找到一个uid的所有地址的集合
	List<Address> getAddressesByUid(int uid);
	
	int deleteAddressByAddressId(int addressId);
	int getTheUserMaxAddressId(int uid);
	
	boolean setAddressDefault(int addressId,int uid);
	/**
	 * 根据一个地址id得到一个完整的地址
	 * @return
	 */
	Address getAddressByAddressId(int addressId);
	
	int changeAddressToInVisible(int addressId);
}
