package com.daniel.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.daniel.api.domain.Category;
import com.daniel.api.dto.CategoryDTO;
import com.daniel.api.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<Category> categories = categoryService.findAll();
		List<CategoryDTO> categoriesDTO = categories.stream().map(category -> new CategoryDTO(category))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(categoriesDTO);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoryDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		Page<Category> categories = categoryService.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoryDTO> categoriesDTO = categories.map(category -> new CategoryDTO(category));

		return ResponseEntity.ok().body(categoriesDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> find(@PathVariable Integer id) {
		Category category = categoryService.find(id);

		return ResponseEntity.ok().body(category);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoryDTO categoryDto) {
		Category category = categoryService.fromDTO(categoryDto);
		category = categoryService.insert(category);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "{id}")
	public ResponseEntity<Category> update(@Valid @RequestBody CategoryDTO categoryDto, @PathVariable Integer id) {
		Category category = categoryService.fromDTO(categoryDto);
		
		category.setId(id);

		category = categoryService.update(category);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		categoryService.delete(id);

		return ResponseEntity.noContent().build();
	}
}
