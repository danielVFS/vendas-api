package com.daniel.api.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.daniel.api.domain.Customer;
import com.daniel.api.dto.CustomerDTO;
import com.daniel.api.repositories.CustomerRepository;
import com.daniel.api.resources.exception.FieldMessage;

public class CustomerUpdateValidator implements ConstraintValidator<CustomerUpdate, CustomerDTO> {
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public void initialize(CustomerUpdate ann) {
	}

	@Override
	public boolean isValid(CustomerDTO customerDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Customer customerAux = customerRepository.findByEmail(customerDto.getEmail());
		if(customerAux != null && !customerAux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "this e-mail already exists"));
		}
			
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldMessage())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}