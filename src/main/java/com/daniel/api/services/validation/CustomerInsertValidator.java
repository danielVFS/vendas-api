package com.daniel.api.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.daniel.api.dto.CustomerNewDTO;
import com.daniel.api.enums.TypeCustomer;
import com.daniel.api.resources.exception.FieldMessage;
import com.daniel.api.services.validation.utils.BR;

public class CustomerInsertValidator implements ConstraintValidator<CustomerInsert, CustomerNewDTO> {
	@Override
	public void initialize(CustomerInsert ann) {
	}

	@Override
	public boolean isValid(CustomerNewDTO customerDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(customerDto.getType().equals(TypeCustomer.NATURALPERSON.getCod()) && !BR.isValidCPF(customerDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CPF is invalid"));
		}
		
		if(customerDto.getType().equals(TypeCustomer.LEGALPERSON.getCod()) && !BR.isValidCNPJ(customerDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CPF is invalid"));
		}
			
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldMessage())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}