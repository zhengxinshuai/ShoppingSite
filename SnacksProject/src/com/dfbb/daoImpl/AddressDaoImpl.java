package com.dfbb.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dfbb.dao.AddressDao;
import com.dfbb.entity.Address;
import com.dfbb.entity.Areas;
import com.dfbb.entity.City;
import com.dfbb.entity.Province;
import com.dfbb.tool.DataSourceTool;



public class AddressDaoImpl implements AddressDao{

	public List<Province> getAllProvince(Connection connection) {
		String sql="select * from provinces";
		
		PreparedStatement ps =null;
		ResultSet rs=null;
		List<Province> provinces=new ArrayList<Province>();
		try {
			ps = connection.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				int id= rs.getInt("id");
				int provinceId=rs.getInt("provinceid");
				String pStr=rs.getString("province");
				Province province=new Province(id, provinceId, pStr);
				provinces.add(province);	
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return provinces;
	}

	public List<City> getCityByPId(Connection connection, int PId) {
	String sql="select * from cities where provinceid=?";
		
		PreparedStatement ps =null;
		ResultSet rs=null;
		List<City> cities=new ArrayList<City>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, PId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id= rs.getInt("id");
				int cityId=rs.getInt("cityid");
				String citystr=rs.getString("city");
				int provinceId= rs.getInt("provinceid");
				City city=new City(id, cityId, citystr, provinceId);
				cities.add(city);
				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return cities;
	
	}

	public List<Areas> getAreasByCityId(Connection connection, int CId) {
		String sql = "select * from areas where cityid=?";

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Areas> areas = new ArrayList<Areas>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, CId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int areaId = rs.getInt("areaid");
				String areastr = rs.getString("area");
				int cityId = rs.getInt("cityid");
				Areas areas2=new Areas(id, areaId, areastr, cityId);
				areas.add(areas2);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return areas;

	}

	@Override
	public int addAddress(Connection connection, Address address) {
		String sql="insert into address (`userid`,`country`,`provinceid`,`cityid`,`areaid`,`street`,`contactperson`,`phonenum`,`default`) values(?,?,?,?,?,?,?,?,'N')";
		Object[] params={address.getUserId(),address.getCountry(),address.getProvinceid(),address.getCityid(),address.getAreaid(),address.getStreet(),address.getContactPerson(),address.getPhoneNum()};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public List<Address> getAddressesByUid(Connection connection, int uid) {
		String sql = "select * from address where userid=?";

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Address> addresses = new ArrayList<Address>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, uid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int addressid= rs.getInt("addressid");
				int userId= rs.getInt("userid");
				String country= rs.getString("country");
				int provinceId= rs.getInt("provinceid");
				int cityId= rs.getInt("cityId");
				int areaId= rs.getInt("areaId");
				String street= rs.getString("street");
				String contactPerson= rs.getString("contactperson");
				String phoneNum= rs.getString("phonenum");
				String isDefault= rs.getString("default");
				String isShow = rs.getString("isShow");
				Address address=new Address();
				address.setIsDefault(isDefault);
				address.setAddressId(addressid);
				address.setUserId(userId);
				address.setCountry(country);
				address.setProvinceid(provinceId);
				address.setCityid(cityId);
				address.setAreaid(areaId);
				address.setStreet(street);
				address.setContactPerson(contactPerson);
				address.setPhoneNum(phoneNum);
				address.setIsShow(isShow);
				addresses.add(address);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return addresses;
	}

	@Override
	public String getPNameByPid(Connection connection, int pid) {
		String sql = "select `province` from provinces where provinceid=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String province=null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, pid);
			rs = ps.executeQuery();
			if (rs.next()) {
				province= rs.getString("province");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return province;
	}

	@Override
	public String getCNameByCid(Connection connection, int cid) {
		String sql = "select `city` from cities where cityid=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String city=null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, cid);
			rs = ps.executeQuery();
			if (rs.next()) {
				city= rs.getString("city");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return city;
	}

	
	public String getANameByAid(Connection connection, int aid) {
		String sql = "select `area` from areas where areaid=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String area=null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, aid);
			rs = ps.executeQuery();
			if (rs.next()) {
				area= rs.getString("area");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return area;
	}

	@Override
	public int deleteAddressByAddressId(Connection connection, int addressId) {
		String sql="delete from address where addressid=?";
		Object[] params={addressId};
		int res=DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public int getTheUserMaxAddressId(Connection connection, int uid) {
		String sql = "select max(addressid) 'addressid' from address where userid=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int addressId=-1;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, uid);
			rs = ps.executeQuery();
			if (rs.next()) {
				addressId=rs.getInt("addressid");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return addressId;
	}

	@Override
	public int setAddressDefault(Connection connection,int addressId) {
		String sql="update address set `default`='Y' where addressid=?";
		Object[] params={addressId};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public int setOtherAddressDefaultN(Connection connection, int addressId, int userId) {
		String sql="update address set `default`='N' where addressId<>? and userId=?";
		Object[] params={addressId,userId};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

	@Override
	public Address getAddressByAddressId(Connection connection, int addressId) {
		String sql = "select * from address where addressid=?";

		PreparedStatement ps = null;
		ResultSet rs = null;
		Address address=null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, addressId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int addressid= rs.getInt("addressid");
				int userId= rs.getInt("userid");
				String country= rs.getString("country");
				int provinceId= rs.getInt("provinceid");
				int cityId= rs.getInt("cityId");
				int areaId= rs.getInt("areaId");
				String street= rs.getString("street");
				String contactPerson= rs.getString("contactperson");
				String phoneNum= rs.getString("phonenum");
				String isDefault= rs.getString("default");
				String isShow = rs.getString("isShow");
				address=new Address();
				address.setIsDefault(isDefault);
				address.setAddressId(addressid);
				address.setUserId(userId);
				address.setCountry(country);
				address.setProvinceid(provinceId);
				address.setCityid(cityId);
				address.setAreaid(areaId);
				address.setStreet(street);
				address.setContactPerson(contactPerson);
				address.setPhoneNum(phoneNum);
				address.setIsShow(isShow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataSourceTool.closeAll(null, ps, rs);
		}
		return address;
	}

	@Override
	public int changeAddressToInVisible(Connection connection, int addressId) {
		String sql="update address set `isShow`='N' where addressid=?";
		Object[] params={addressId};
		int res= DataSourceTool.exceuteUpdate(connection, sql, params);
		return res;
	}

}
