package com.daniel.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.api.domain.Category;
import com.daniel.api.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> find(@PathVariable Integer id){
		Category category = categoryService.find(id);
		
		return ResponseEntity.ok().body(category);
	}
}
