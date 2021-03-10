package com.daniel.api.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.api.domain.Adress;
import com.daniel.api.domain.Category;
import com.daniel.api.domain.City;
import com.daniel.api.domain.Customer;
import com.daniel.api.domain.ItemOrder;
import com.daniel.api.domain.Order;
import com.daniel.api.domain.Payment;
import com.daniel.api.domain.PaymentWithBoleto;
import com.daniel.api.domain.PaymentWithCreditCard;
import com.daniel.api.domain.Product;
import com.daniel.api.domain.State;
import com.daniel.api.enums.StatePayment;
import com.daniel.api.enums.TypeCustomer;
import com.daniel.api.repositories.AdressRepository;
import com.daniel.api.repositories.CategoryRepository;
import com.daniel.api.repositories.CityRepository;
import com.daniel.api.repositories.CustomerRepository;
import com.daniel.api.repositories.ItemOrderRepository;
import com.daniel.api.repositories.OrderRepository;
import com.daniel.api.repositories.PaymentRepository;
import com.daniel.api.repositories.ProductRepository;
import com.daniel.api.repositories.StateRepository;

@Service
public class DBService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AdressRepository adressRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ItemOrderRepository itemOrderRepository;

	public void instatiateTestDatabase() throws ParseException {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		Category cat3 = new Category(null, "Casa mesa e banho");
		Category cat4 = new Category(null, "Eletrônicos");
		Category cat5 = new Category(null, "Jardinagem");
		Category cat6 = new Category(null, "Decoração");

		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Mesa de escritório", 300.00);
		Product p5 = new Product(null, "Toalha", 50.00);
		Product p6 = new Product(null, "Colcha", 200.00);
		Product p7 = new Product(null, "TV true color", 1200.00);
		Product p8 = new Product(null, "Roçadeira", 800.00);
		Product p9 = new Product(null, "Abajour", 100.00);
		Product p10 = new Product(null, "Pendente", 180.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));

		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));

		/*******************************************************/

		State st1 = new State(null, "Minas Gerais");
		State st2 = new State(null, "São Paulo");

		City c1 = new City(null, "Uberlândia", st1);
		City c2 = new City(null, "São Paulo", st2);
		City c3 = new City(null, "Campinas", st2);

		st1.getCities().addAll(Arrays.asList(c1));
		st2.getCities().addAll(Arrays.asList(c2, c3));

		stateRepository.saveAll(Arrays.asList(st1, st2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));

		/*******************************************************/

		Customer cust1 = new Customer(null, "Maria Silva", "danielv759153@gmail.com", "36378912377",
				TypeCustomer.NATURALPERSON);

		cust1.getPhones().addAll(Arrays.asList("93592151", "98526312"));

		Adress ad1 = new Adress(null, "Rua Flores", "300", "Apto 101", "Jardim", "75120000", cust1, c1);
		Adress ad2 = new Adress(null, "Avenida Matos", "105", "Sala 800", "Centro", "75190000", cust1, c2);

		cust1.getAdresses().addAll(Arrays.asList(ad1, ad2));

		customerRepository.saveAll(Arrays.asList(cust1));
		adressRepository.saveAll(Arrays.asList(ad1, ad2));

		/*******************************************************/

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Order ord1 = new Order(null, sdf.parse("30/03/2021 10:32"), cust1, ad1);
		Order ord2 = new Order(null, sdf.parse("10/04/2021 19:35"), cust1, ad2);

		Payment pay1 = new PaymentWithCreditCard(null, StatePayment.SETTLED, ord1, 6);
		ord1.setPayment(pay1);

		Payment pay2 = new PaymentWithBoleto(null, StatePayment.PENDIND, ord2, sdf.parse("15/04/2021 00:00"), null);
		ord2.setPayment(pay2);

		cust1.getOrders().addAll(Arrays.asList(ord1, ord2));

		orderRepository.saveAll(Arrays.asList(ord1, ord2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));

		/*******************************************************/

		ItemOrder io1 = new ItemOrder(ord1, p1, 0.00, 1, 2000.00);
		ItemOrder io2 = new ItemOrder(ord1, p3, 0.00, 2, 80.00);
		ItemOrder io3 = new ItemOrder(ord2, p2, 100.00, 1, 800.00);

		ord1.getItens().addAll(Arrays.asList(io1, io2));
		ord2.getItens().addAll(Arrays.asList(io3));

		p1.getItens().addAll(Arrays.asList(io1));
		p2.getItens().addAll(Arrays.asList(io2));
		p3.getItens().addAll(Arrays.asList(io3));

		itemOrderRepository.saveAll(Arrays.asList(io1, io2, io3));

		/*******************************************************/
	}
}
