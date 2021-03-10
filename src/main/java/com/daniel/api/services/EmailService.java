package com.daniel.api.services;

import org.springframework.mail.SimpleMailMessage;

import com.daniel.api.domain.Order;

public interface EmailService {
	
	void sendOrderEmailConfirmation(Order order);
	
	void sendEmail(SimpleMailMessage msg);
}


