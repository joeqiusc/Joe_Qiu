package com.revature.daos;

import java.util.List;

public interface DAO<T> {

	List<T> getAll();
	T getById(int id);
	T add(T obj);
	T update(T updatedObj);
	boolean delete(int id);
	
}
