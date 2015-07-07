package com.eleks.service;

public interface BaseService<T> {
	void save(T entity);

	void save(Iterable<T> entities);

	Iterable<T> findAll();

	T findOne(Long id);
}
