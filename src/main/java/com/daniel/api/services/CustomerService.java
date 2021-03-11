package com.daniel.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.api.domain.Adress;
import com.daniel.api.domain.City;
import com.daniel.api.domain.Customer;
import com.daniel.api.dto.CustomerDTO;
import com.daniel.api.dto.CustomerNewDTO;
import com.daniel.api.enums.TypeCustomer;
import com.daniel.api.repositories.AdressRepository;
import com.daniel.api.repositories.CustomerRepository;
import com.daniel.api.services.exceptions.DataIntegrityException;
import com.daniel.api.services.exceptions.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AdressRepository adressRepository;
	@Autowired
	private BCryptPasswordEncoder bcrypt;

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

	@Transactional
	public Customer insert(Customer customer) {
		customer.setId(null);
		customer = customerRepository.save(customer);
		
		adressRepository.saveAll(customer.getAdresses());
		
		return customer;
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
			throw new DataIntegrityException("It is not possible to delete a Customer that has related entities");
		}
	}

	public Customer fromDTO(CustomerDTO customerDto) {
		return new Customer(customerDto.getId(), customerDto.getName(), customerDto.getEmail(), null, null, null);
	}

	public Customer fromDTO(CustomerNewDTO customerDto) {
		Customer customer = new Customer(null, customerDto.getName(), customerDto.getEmail(),
				customerDto.getCpfOrCnpj(), TypeCustomer.toEnum(customerDto.getType()), bcrypt.encode(customerDto.getPassword()));

		City city = new City(customerDto.getCidadeId(), null, null);

		Adress adress = new Adress(null, customerDto.getAdress(), customerDto.getNumber(), customerDto.getComplement(),
				customerDto.getDistrict(), customerDto.getZipCode(), customer, city);

		customer.getAdresses().add(adress);
		customer.getPhones().add(customerDto.getPhone1());

		if (customerDto.getPhone2() != null) {
			customer.getPhones().add(customerDto.getPhone2());
		}
		if (customerDto.getPhone3() != null) {
			customer.getPhones().add(customerDto.getPhone3());
		}

		return customer;
	}

	private void updateData(Customer newCustomer, Customer customer) {
		newCustomer.setName(customer.getName());
		newCustomer.setEmail(customer.getEmail());
	}

}
