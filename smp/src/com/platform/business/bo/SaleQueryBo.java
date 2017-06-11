package com.platform.business.bo;

import java.util.Date;

public class SaleQueryBo {

	private Date saleTimeFrom;
	
	private Date saleTimeEnd;
	
	private String saleUserId;
	
	private String saleUserName;
	
	private String productId;
	
	


	public String getSaleUserId() {
		return saleUserId;
	}

	public void setSaleUserId(String saleUserId) {
		this.saleUserId = saleUserId;
	}

	public String getSaleUserName() {
		return saleUserName;
	}

	public void setSaleUserName(String saleUserName) {
		this.saleUserName = saleUserName;
	}

	public Date getSaleTimeFrom() {
		return saleTimeFrom;
	}

	public void setSaleTimeFrom(Date saleTimeFrom) {
		this.saleTimeFrom = saleTimeFrom;
	}

	public Date getSaleTimeEnd() {
		return saleTimeEnd;
	}

	public void setSaleTimeEnd(Date saleTimeEnd) {
		this.saleTimeEnd = saleTimeEnd;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
