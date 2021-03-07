package com.daniel.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.api.domain.Category;
import com.daniel.api.domain.Customer;
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

}
