package com.daniel.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;

import com.daniel.api.domain.Customer;
import com.daniel.api.services.validation.CustomerUpdate;

@CustomerUpdate
public class CustomerDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "Name is required")
	@Length(min = 5, max = 80, message = "The length must be between 5 and 8 characters ")
	private String name;
	
	@NotEmpty(message = "Email is required")
	@Email(message = "Invalid E-mail")
	private String email;
	
	public CustomerDTO() {
		super();
	}
	
	public CustomerDTO(Customer customer) {
		id = customer.getId();
		name = customer.getName();
		email = customer.getEmail();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
