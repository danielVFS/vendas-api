package com.daniel.api.domain;

import com.daniel.api.enums.StatePayment;

public class PaymentWithCreditCard extends Payment{
	
	private static final long serialVersionUID = 1L;
	
	public Integer numberOfInstallments;

	public PaymentWithCreditCard() {
		super();
	}

	public PaymentWithCreditCard(Integer id, StatePayment state, Order order, Integer numberOfInstallments) {
		super(id, state, order);
		this.numberOfInstallments = numberOfInstallments;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}
	
}
