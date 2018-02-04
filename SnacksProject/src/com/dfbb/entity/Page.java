package com.dfbb.entity;

public class Page{
	private int perPageCount;//每页的条数 第一个设置
	
	
	private int totalItemCount;//所有的条数  设置了总条数，就设置了总页数
	private int totalPageNum;//总页数
	
	
	
	
	
	
	private int currentPageNo;//设置当前的页码 就设置了开始的下标
	private int startIndex;//开始查找的index
	
	
	
	//这里是业务拓展
	private String snackJson;//查找到的零食的json 里面有着零食集合
	private String orderJson;//查找的到的订单的json 里面有着查找到的订单集合
	
	
	
	
	public String getOrderJson() {
		return orderJson;
	}

	public void setOrderJson(String orderJson) {
		this.orderJson = orderJson;
	}

	public String getSnackJson() {
		return snackJson;
	}

	public void setSnackJson(String snackJson) {
		this.snackJson = snackJson;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public int getPerPageCount() {
		return perPageCount;
	}
	/**
	 * 这个属性在其他地方有用到，第一个设置
	 * @param perPageCount
	 */
	public void setPerPageCount(int perPageCount) {
		this.perPageCount = perPageCount;
	}
	public int getTotalItemCount() {
		return totalItemCount;
	}
	/**
	 * 设置总条数，同时设置页数
	 * @param totalItemCount
	 */
	public void setTotalItemCount(int totalItemCount) {
		if(totalItemCount>0){
			this.totalItemCount=totalItemCount;
			//设置总页数
			this.totalPageNum=this.totalItemCount%perPageCount==0?(this.totalItemCount/perPageCount):(this.totalItemCount/perPageCount+1);
			
		}
		
		this.totalItemCount = totalItemCount;
	}
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	/**
	 * 设置当前页数 同时设定了开始的下标
	 * @param currentPageNo
	 */
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
		this.startIndex=(currentPageNo-1)*perPageCount;
	}
	public int getStartIndex() {
		return startIndex;
	}
	
	
}
