package com.daniel.api.domain;

import javax.persistence.Entity;

import com.daniel.api.enums.StatePayment;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("paymentWithCreditCard")
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
