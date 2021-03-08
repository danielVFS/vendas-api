package com.daniel.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.daniel.api.domain.Category;
import com.daniel.api.dto.CategoryDTO;
import com.daniel.api.repositories.CategoryRepository;
import com.daniel.api.services.exceptions.DataIntegrityException;
import com.daniel.api.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> findAll() {
		List<Category> categories = categoryRepository.findAll();

		return categories;
	}

	public Category find(Integer id) {
		Optional<Category> category = categoryRepository.findById(id);

		return category.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado, ID:" + id + ", Tipo: " + Category.class.getName()));
	}
	
	public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoryRepository.findAll(pageRequest);
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

		try {
			categoryRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("It is not possible to delete a Category that has products");
		}
	}
	
	public Category fromDTO(CategoryDTO categoryDto) {
		return new Category(categoryDto.getId(), categoryDto.getName());
	}
}
