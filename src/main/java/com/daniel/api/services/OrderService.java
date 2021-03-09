package com.daniel.api.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.api.domain.ItemOrder;
import com.daniel.api.domain.Order;
import com.daniel.api.domain.PaymentWithBoleto;
import com.daniel.api.enums.StatePayment;
import com.daniel.api.repositories.ItemOrderRepository;
import com.daniel.api.repositories.OrderRepository;
import com.daniel.api.repositories.PaymentRepository;
import com.daniel.api.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ItemOrderRepository itemOrderRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private BoletoService boletoService;

	public Order find(Integer id) {
		Optional<Order> order = orderRepository.findById(id);

		return order.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado, ID:" + id + ", Tipo: " + Order.class.getName()));
	}

	@Transactional
	public Order insert(Order order) {
		order.setId(null);
		order.setInstant(new Date());
		order.getPayment().setState(StatePayment.PENDIND);
		order.getPayment().setOrder(order);

		if (order.getPayment() instanceof PaymentWithBoleto) {
			PaymentWithBoleto payment = (PaymentWithBoleto) order.getPayment();
			boletoService.fillPaymentWithBoleto(payment, order.getInstant());
		}

		order = orderRepository.save(order);
		paymentRepository.save(order.getPayment());

		for (ItemOrder io : order.getItens()) {
			io.setDiscount(0.0);
			io.setPrice(productService.find(io.getProduct().getId()).getPrice());
			io.setOrder(order);
		}

		itemOrderRepository.saveAll(order.getItens());
		return order;
	}
}
