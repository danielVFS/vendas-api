package com.daniel.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.api.domain.Order;
import com.daniel.api.repositories.OrderRepository;
import com.daniel.api.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Order find(Integer id) {
		Optional<Order> order = orderRepository.findById(id);
		
		return order.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado, ID:" + id + ", Tipo: " + Order.class.getName()));
	}
}
