package com.eleks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eleks.model.db.Movement;

@Repository
public interface MovementRepository extends CrudRepository<Movement, Long> {

}
