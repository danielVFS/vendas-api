package com.daniel.api.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.api.domain.Customer;
import com.daniel.api.dto.CustomerDTO;
import com.daniel.api.services.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {

	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Customer> find(@PathVariable Integer id) {
		Customer customer = customerService.find(id);

		return ResponseEntity.ok().body(customer);
	}

	@GetMapping
	public ResponseEntity<List<CustomerDTO>> findAll() {
		List<Customer> categories = customerService.findAll();
		List<CustomerDTO> categoriesDTO = categories.stream().map(category -> new CustomerDTO(category))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(categoriesDTO);
	}

	@GetMapping(value = "/page")
	public ResponseEntity<Page<CustomerDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<Customer> categories = customerService.findPage(page, linesPerPage, orderBy, direction);
		Page<CustomerDTO> categoriesDTO = categories.map(category -> new CustomerDTO(category));

		return ResponseEntity.ok().body(categoriesDTO);
	}
	
	

	@PutMapping(value = "{id}")
	public ResponseEntity<Customer> update(@Valid @RequestBody CustomerDTO categoryDto, @PathVariable Integer id) {
		Customer category = customerService.fromDTO(categoryDto);

		category.setId(id);

		category = customerService.update(category);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		customerService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
