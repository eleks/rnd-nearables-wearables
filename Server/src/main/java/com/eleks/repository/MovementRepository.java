package com.eleks.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eleks.model.db.Movement;

@Repository
public interface MovementRepository extends CrudRepository<Movement, Long> {
	//SELECT * FROM movement n
	//WHERE timestamp=(SELECT MAX(timestamp)
	//	    FROM movement WHERE user_id=n.user_id)
	@Query("SELECT m FROM Movement m  WHERE m.timestamp = (SELECT MAX(timestamp) FROM Movement m2 WHERE m2.user = m.user)")
	Iterable<Movement> findAllOrderByTimestampDesc();
}
