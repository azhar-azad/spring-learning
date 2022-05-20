package com.azad.cineplex2.services;

import java.util.List;

public interface GenericService<T> {

	T create(T object);
	List<T> getAll(int page, int limit);
	List<T> getAll(int page, int limit, String sort, String order);
	T getById(Long id);
	T updateById(Long id, T updatedObject);
	T update(T object, T updatedObject);
	void deleteById(Long id);
	void delete(T object);
}
