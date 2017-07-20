package com.platform.business.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="SALARY")
public class Salary {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid",strategy="uuid")
	@Column(name = "ID")
	private String id;
	
	@Column(name="YEARMONTH")
	private String yearMonth;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="USER_LOGINNAME")
	private String userLoginName;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="AMOUNT")
	private Double amount;
	
	@Column(name="CUMULATIVE_AMOUNT")
	private Double comulativeAmount;
	
	@Column(name="COUNT28")
	private Integer count28;
	
	@Column(name="USER_LEVEL")
	private String userLevel;
	
	@Column(name="REBATE_STANDARD")
	private Double rebateStandard;
	
	@Column(name="SALARY_A")
	private Double salaryA;
	
	@Column(name="SALARY_B")
	private Double salaryB;
	
	@Column(name="SALARY_C")
	private Double salaryC;
	
	@Column(name="SALARY_D")
	private Double salaryD;
	
	@Column(name="SALARY_TOTAL")
	private Double salaryTotal;
	
	@Column(name="CALCU_TIME")
	private Date calcuTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

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

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getComulativeAmount() {
		return comulativeAmount;
	}

	public void setComulativeAmount(Double comulativeAmount) {
		this.comulativeAmount = comulativeAmount;
	}

	public Integer getCount28() {
		return count28;
	}

	public void setCount28(Integer count28) {
		this.count28 = count28;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public Double getRebateStandard() {
		return rebateStandard;
	}

	public void setRebateStandard(Double rebateStandard) {
		this.rebateStandard = rebateStandard;
	}

	public Double getSalaryA() {
		return salaryA;
	}

	public void setSalaryA(Double salaryA) {
		this.salaryA = salaryA;
	}

	public Double getSalaryB() {
		return salaryB;
	}

	public void setSalaryB(Double salaryB) {
		this.salaryB = salaryB;
	}

	public Double getSalaryC() {
		return salaryC;
	}

	public void setSalaryC(Double salaryC) {
		this.salaryC = salaryC;
	}

	public Double getSalaryD() {
		return salaryD;
	}

	public void setSalaryD(Double salaryD) {
		this.salaryD = salaryD;
	}

	public Double getSalaryTotal() {
		return salaryTotal;
	}

	public void setSalaryTotal(Double salaryTotal) {
		this.salaryTotal = salaryTotal;
	}

	public Date getCalcuTime() {
		return calcuTime;
	}

	public void setCalcuTime(Date calcuTime) {
		this.calcuTime = calcuTime;
	}
	
}
