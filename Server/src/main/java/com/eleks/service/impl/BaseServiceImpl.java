package com.eleks.service.impl;

import org.springframework.data.repository.CrudRepository;

import com.eleks.service.BaseService;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
	public abstract CrudRepository<T, Long> getRepository();

	public void save(T entity) {
		getRepository().save(entity);
	}

	public void save(Iterable<T> entities) {
		getRepository().save(entities);
	}

	public Iterable<T> findAll() {
		return getRepository().findAll();
	}

	public T findOne(Long id) {
		return getRepository().findOne(id);
	}
}
