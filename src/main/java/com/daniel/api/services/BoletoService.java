package com.daniel.api.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.daniel.api.domain.PaymentWithBoleto;

@Service
public class BoletoService {

	public void fillPaymentWithBoleto(PaymentWithBoleto payment, Date orderInstance){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(orderInstance);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		payment.setPaymentDate(calendar.getTime());
	}
}
