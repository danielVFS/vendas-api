package com.daniel.api.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.daniel.api.domain.Order;
import com.daniel.api.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	@Autowired
	private OrderService orderService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> find(@PathVariable Integer id) {
		Order order = orderService.find(id);

		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping
	public ResponseEntity<Page<Order>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "instant") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		
		Page<Order> categories = orderService.findPage(page, linesPerPage, orderBy, direction);

		return ResponseEntity.ok().body(categories);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Order order) {
		order = orderService.insert(order);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
}
