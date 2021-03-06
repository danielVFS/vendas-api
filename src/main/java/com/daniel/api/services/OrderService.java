package com.daniel.api.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.api.domain.Customer;
import com.daniel.api.domain.ItemOrder;
import com.daniel.api.domain.Order;
import com.daniel.api.domain.PaymentWithBoleto;
import com.daniel.api.enums.StatePayment;
import com.daniel.api.repositories.ItemOrderRepository;
import com.daniel.api.repositories.OrderRepository;
import com.daniel.api.repositories.PaymentRepository;
import com.daniel.api.secutiry.UserSS;
import com.daniel.api.services.exceptions.AuthorizationException;
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
	private CustomerService customerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private EmailService emailService; // o spring procurar o bin que utiliza EmailService

	public Order find(Integer id) {
		Optional<Order> order = orderRepository.findById(id);

		return order.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado, ID:" + id + ", Tipo: " + Order.class.getName()));
	}

	@Transactional
	public Order insert(Order order) {
		order.setId(null);
		order.setInstant(new Date());
		order.setCustomer(customerService.find(order.getCustomer().getId()));
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
			io.setProduct(productService.find(io.getProduct().getId()));
			io.setPrice(io.getProduct().getPrice());
			io.setOrder(order);
		}

		itemOrderRepository.saveAll(order.getItens());
		emailService.sendOrderEmailConfirmation(order);
		return order;
	}
	
	public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		
		if(user == null) {
			throw new AuthorizationException("Access denied");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Customer customer = customerService.find(user.getId());	
		return orderRepository.findByCustomer(customer, pageRequest);
	}
}
