package com.daniel.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.daniel.api.domain.PaymentWithBoleto;
import com.daniel.api.domain.PaymentWithCreditCard;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PaymentWithCreditCard.class);
				objectMapper.registerSubtypes(PaymentWithBoleto.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}