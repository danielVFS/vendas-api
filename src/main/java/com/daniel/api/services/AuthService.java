package com.daniel.api.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.daniel.api.domain.Customer;
import com.daniel.api.repositories.CustomerRepository;
import com.daniel.api.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Customer customer = customerRepository.findByEmail(email);
		if (customer == null) {
			throw new ObjectNotFoundException("Email not found");
		}
		
		String newPassword = newPassword();
		customer.setPassword(bcrypt.encode(newPassword));
		
		customerRepository.save(customer);
		emailService.sendNewPasswordEmail(customer, newPassword);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
