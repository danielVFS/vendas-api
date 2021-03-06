package com.daniel.api.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.daniel.api.enums.StatePayment;

@Entity
public class Payment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;

	private StatePayment state;

	@OneToOne
	@JoinColumn(name = "order_id")
	@MapsId
	private Order order;

	public Payment() {
		super();
	}

	public Payment(Integer id, StatePayment state, Order order) {
		super();
		this.id = id;
		this.state = state;
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StatePayment getState() {
		return state;
	}

	public void setState(StatePayment state) {
		this.state = state;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}