package com.daniel.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.api.domain.Customer;
import com.daniel.api.repositories.CustomerRepository;
import com.daniel.api.services.exceptions.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public Customer find(Integer id) {
		Optional<Customer> customer = customerRepository.findById(id);

		return customer.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado, ID:" + id + ", Tipo: " + Customer.class.getName()));
	}

}
