package com.daniel.api.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "ORDER_TABLE")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date instant;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
	private Payment payment;

	@ManyToOne
	@JoinColumn(name = "costumer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "delivery_adress_id")
	private Adress deliveryAdress;

	@OneToMany(mappedBy = "id.order")	
	private Set<ItemOrder> itens = new HashSet<>();
	
	public Order() {
		super();
	}

	public Order(Integer id, Date instant, Customer customer, Adress deliveryAdress) {
		super();
		this.id = id;
		this.instant = instant;
		this.customer = customer;
		this.deliveryAdress = deliveryAdress;
	}
	
	public double getTotalValue(){
		double sum = 0.0;
		
		for(ItemOrder io : itens) {
			sum = sum + io.getSubTotal();
		}
		
		return sum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstant() {
		return instant;
	}

	public void setInstant(Date instant) {
		this.instant = instant;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Adress getDeliveryAdress() {
		return deliveryAdress;
	}

	public void setDeliveryAdress(Adress deliveryAdress) {
		this.deliveryAdress = deliveryAdress;
	}
	
	@OneToMany(mappedBy = "id.order")
	public Set<ItemOrder> getItens() {
		return itens;
	}

	public void setItens(Set<ItemOrder> itens) {
		this.itens = itens;
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
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
