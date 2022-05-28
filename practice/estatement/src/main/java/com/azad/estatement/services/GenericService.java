package com.azad.estatement.services;

import java.util.List;

import com.azad.estatement.utils.PagingAndSorting;

public interface GenericService<T> {

	T create(T object);
	List<T> getAll(PagingAndSorting ps);
	T getById(Long id);
	T updateById(Long id, T object);
	void deleteById(Long id);
}
