package com.dfbb.entity;

/**
 * 城市
 * @author apple
 *
 */
public class City {
	private int id;
	private int cityid;
	private String city;
	private int provinceId;
	
	public City(int id, int cityid, String city, int provinceId) {
		super();
		this.id = id;
		this.cityid = cityid;
		this.city = city;
		this.provinceId = provinceId;
	}
	public City(){}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCityid() {
		return cityid;
	}
	public void setCityid(int cityid) {
		this.cityid = cityid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	
	
	
	
}
