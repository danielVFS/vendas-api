package com.daniel.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.api.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

}
