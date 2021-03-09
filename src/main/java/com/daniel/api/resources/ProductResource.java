package com.daniel.api.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.api.domain.Product;
import com.daniel.api.dto.ProductDTO;
import com.daniel.api.resources.utils.URL;
import com.daniel.api.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> find(@PathVariable Integer id) {
		Product product = productService.find(id);

		return ResponseEntity.ok().body(product);
	}

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findPage(@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categories", defaultValue = "") String categories,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		String nameDecoded = URL.decodeParam(name);
		List<Integer> ids = URL.decodeIntList(categories);

		Page<Product> products = productService.search(nameDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProductDTO> productsDTO = products.map(product -> new ProductDTO(product));

		return ResponseEntity.ok().body(productsDTO);
	}
}
