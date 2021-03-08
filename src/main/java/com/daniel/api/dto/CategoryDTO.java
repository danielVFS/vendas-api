package com.daniel.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.daniel.api.domain.Category;

public class CategoryDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Name is required")
	@Length(min = 5, max = 80, message = "The length must be between 5 and 8 characters ")
	private String name;

	public CategoryDTO() {
		super();
	}
	
	public CategoryDTO(Category category) {
		id = category.getId();
		name = category.getName();
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
	
}
