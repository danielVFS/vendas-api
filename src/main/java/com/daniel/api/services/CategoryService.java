package com.daniel.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.api.domain.Category;
import com.daniel.api.repositories.CategoryRepository;
import com.daniel.api.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public Category find(Integer id) {
		Optional<Category> category = categoryRepository.findById(id);

		return category.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado, ID:" + id + ", Tipo: " + Category.class.getName()));
	}

	public Category insert(Category category) {
		category.setId(null);
		return categoryRepository.save(category);
	}

	public Category update(Category category) {
		find(category.getId());
		
		return categoryRepository.save(category);
	}
	
	public void delete(Integer id) {
		find(id);
		
		categoryRepository.deleteById(id);
	}
}
