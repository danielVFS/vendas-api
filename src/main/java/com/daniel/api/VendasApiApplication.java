package com.daniel.api;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.daniel.api.domain.Adress;
import com.daniel.api.domain.Category;
import com.daniel.api.domain.City;
import com.daniel.api.domain.Customer;
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
import com.daniel.api.repositories.OrderRepository;
import com.daniel.api.repositories.PaymentRepository;
import com.daniel.api.repositories.ProductRepository;
import com.daniel.api.repositories.StateRepository;

@SpringBootApplication
public class VendasApiApplication implements CommandLineRunner {

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

	public static void main(String[] args) {
		SpringApplication.run(VendasApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");

		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));

		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));

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
		
		Customer cust1 = new Customer(null, "Maria Silva", "maria@gmail.com", "36378912377", TypeCustomer.NATURALPERSON);
		
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
	}

}
