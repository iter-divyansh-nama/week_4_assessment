package com.capgemini.model.entity;

public class TransactionRequest {

	private Integer accountId;
	private Double amount;

	public TransactionRequest() {
		super();
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
