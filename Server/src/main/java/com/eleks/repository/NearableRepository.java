package com.eleks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eleks.model.db.Nearable;

@Repository
public interface NearableRepository extends CrudRepository<Nearable, Long> {
	Nearable findByUid(String uid);
}
