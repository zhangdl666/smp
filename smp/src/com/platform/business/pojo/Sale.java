package com.platform.business.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="SALE")
public class Sale {
	
	public static final String RECORD_TYPE_TOTAL = "total";
	public static final String RECORD_TYPE_DETAIL = "detail";

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid",strategy="uuid")
	@Column(name = "ID")
	private String id;
	
	@Column(name="BUSINESS_NUMBER")
	private String businessNumber;
	
	@Column(name="SALE_TIME")
	private Date saleTime;
	
	@Column(name="SALE_USER_ID")
	private String saleUserId;
	
	@Column(name="SALE_USER_NAME")
	private String saleUserName;
	
	@Column(name="PRODUCT_ID")
	private String productId;
	
	@Column(name="UNIT_PRICE")
	private Double unitPrice;
	
	@Column(name="QUANTITY")
	private Double quantity;
	
	@Column(name="TOTAL")
	private Double total;
	
	@Column(name="RECORD_TYPE")
	private String recordType;
	
	@Column(name="RECORD_USER_ID")
	private String recordUserId;
	
	@Column(name="RECORD_USER_NAME")
	private String recordUserName;
	
	@Column(name="RECORD_TIME")
	private Date recordTime;
	
	@Column(name="VALIDSTATUS")
	private String validstatus;

	public String getRecordUserId() {
		return recordUserId;
	}

	public void setRecordUserId(String recordUserId) {
		this.recordUserId = recordUserId;
	}

	public String getRecordUserName() {
		return recordUserName;
	}

	public void setRecordUserName(String recordUserName) {
		this.recordUserName = recordUserName;
	}

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getValidstatus() {
		return validstatus;
	}

	public void setValidstatus(String validstatus) {
		this.validstatus = validstatus;
	}
	
	
}
