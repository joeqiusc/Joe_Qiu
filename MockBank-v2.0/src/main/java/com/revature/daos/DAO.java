package com.revature.daos;

import java.util.List;
/**
 * @author Joe
 * This is dao interface, which supply the abstract methods for userDao and accountDao;
 * */
public interface DAO <T>{
	List<T> getAll(); 
	T getById(int id);
	T add(T ob);
	T update(T updateOb);
	boolean delete(int id);		
}
