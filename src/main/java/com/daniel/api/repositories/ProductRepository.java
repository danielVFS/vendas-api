package com.daniel.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.api.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
