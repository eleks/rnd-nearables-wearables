package com.eleks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.eleks.model.db.Nearable;
import com.eleks.repository.NearableRepository;
import com.eleks.service.NearableService;

@Service
public class NearableServiceImpl extends BaseServiceImpl<Nearable> implements NearableService {

	@Autowired
	private NearableRepository repo;

	@Override
	public CrudRepository<Nearable, Long> getRepository() {
		return repo;
	}

	@Override
	public Nearable getByUid(String uid) {
		return repo.findByUid(uid);
	}

}
