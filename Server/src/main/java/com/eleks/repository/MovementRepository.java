package com.eleks.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eleks.model.db.Movement;

@Repository
public interface MovementRepository extends CrudRepository<Movement, Long> {
	@Query("SELECT m FROM Movement m GROUP BY m.user ORDER BY m.timestamp desc")
	Iterable<Movement> findAllOrderByTimestampDesc();
}
