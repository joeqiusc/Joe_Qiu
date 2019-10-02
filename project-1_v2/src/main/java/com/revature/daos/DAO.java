package com.revature.daos;

import java.util.List;

public interface DAO <T>{
	
	List<T> getAll();
	T getById(int id);
	T add(T ob);
	T update(T updateOb);
	boolean delete(int id);

}
