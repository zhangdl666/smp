package com.platform.business.bo;

public class SaleStat {

	private String userId;
	
	private String userName;
	
	private String _parentId;
	
	private double total;
	
	private double cumulativeTotal;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String get_parentId() {
		return _parentId;
	}

	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getCumulativeTotal() {
		return cumulativeTotal;
	}

	public void setCumulativeTotal(double cumulativeTotal) {
		this.cumulativeTotal = cumulativeTotal;
	}
	
	
}
