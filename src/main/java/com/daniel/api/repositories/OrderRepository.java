package com.daniel.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.api.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
