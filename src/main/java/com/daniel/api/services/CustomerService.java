package com.daniel.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.daniel.api.domain.Customer;
import com.daniel.api.dto.CustomerDTO;
import com.daniel.api.repositories.CustomerRepository;
import com.daniel.api.services.exceptions.DataIntegrityException;
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

	public List<Customer> findAll() {
		List<Customer> customer = customerRepository.findAll();

		return customer;
	}

	public Page<Customer> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return customerRepository.findAll(pageRequest);
	}

	public Customer update(Customer customer) {
		Customer newCustomer = find(customer.getId());

		updateData(newCustomer, customer);

		return customerRepository.save(newCustomer);
	}

	public void delete(Integer id) {
		find(id);

		try {
			customerRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("It is not possible to delete a Customer that has products");
		}
	}

	public Customer fromDTO(CustomerDTO customerDto) {
		return new Customer(customerDto.getId(), customerDto.getName(), customerDto.getEmail(), null, null);
	}

	private void updateData(Customer newCustomer, Customer customer) {
		newCustomer.setName(customer.getName());
		newCustomer.setEmail(customer.getEmail());
	}

}
