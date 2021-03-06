package com.daniel.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.api.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

}
