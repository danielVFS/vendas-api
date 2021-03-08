package com.daniel.api.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.daniel.api.services.validation.CustomerInsert;

@CustomerInsert
public class CustomerNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Name is required")
	@Length(min = 5, max = 80, message = "The length must be between 5 and 8 characters ")
	private String name;
	
	@NotEmpty(message = "Email is required")
	@Email(message = "Invalid E-mail")
	private String email;
	
	private String cpfOrCnpj;
	private Integer type;
	
	@NotEmpty(message = "Adress is required")
	private String adress;
	
	@NotEmpty(message = "Number is required")
	private String number;
	
	private String complement;
	private String district;
	
	@NotEmpty(message = "zip code is required")
	private String zipCode;

	@NotEmpty(message = "Phoene1 is required")
	private String phone1;
	
	private String phone2;
	private String phone3;

	private Integer cidadeId;

	public CustomerNewDTO() {
		super();
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

	public String getCpfOrCnpj() {
		return cpfOrCnpj;
	}

	public void setCpfOrCnpj(String cpfOrCnpj) {
		this.cpfOrCnpj = cpfOrCnpj;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}

}
