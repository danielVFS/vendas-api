package com.daniel.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.api.domain.Adress;

@Repository
public interface AdressRepository extends JpaRepository<Adress, Integer> {

}
