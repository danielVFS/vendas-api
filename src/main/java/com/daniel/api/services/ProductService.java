package com.daniel.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.daniel.api.domain.Category;
import com.daniel.api.domain.Product;
import com.daniel.api.repositories.CategoryRepository;
import com.daniel.api.repositories.ProductRepository;
import com.daniel.api.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	public Product find(Integer id) {
		Optional<Product> product = productRepository.findById(id);

		return product.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado, ID:" + id + ", Tipo: " + Product.class.getName()));
	}

	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		List<Category> categories = categoryRepository.findAllById(ids);

		return productRepository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
	}

}
