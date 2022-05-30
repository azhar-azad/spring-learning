package com.azad.bankapi.services;

import java.util.List;

import com.azad.bankapi.utils.PagingAndSorting;

public interface GenericApiService<T> {

	T create(T object);
	List<T> getAll(PagingAndSorting ps);
	T getById(Long id);
	T updateById(Long id, T object);
	void deleteById(Long id);
}
