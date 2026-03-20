package com.capgemini.model.dto;

import java.math.BigDecimal;

public class RevenueSummaryDTO {

	private String customerName;
	private BigDecimal totalRevenue;

	public RevenueSummaryDTO() {
		super();
	}

	public RevenueSummaryDTO(String customerName, BigDecimal totalRevenue) {
		super();
		this.customerName = customerName;
		this.totalRevenue = totalRevenue;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

}
