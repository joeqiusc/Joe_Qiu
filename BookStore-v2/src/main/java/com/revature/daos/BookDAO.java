package com.revature.daos;

import static com.revature.AppDriver.currentSession;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Book;

import oracle.jdbc.OracleTypes;

public class BookDAO implements DAO<Book> {
	
	public List<Book> getBooksByTitle(String title) {
        return null;
    }
    
    public List<Book> getBooksByAuthor(String author) {
        return null;
    }
    
    public List<Book> getBooksByGenre(String genre) {
        return null;
    }
    
    public Book getBookByIsbn(String isbn) {
        return null;
    }
    
    @Override
    public List<Book> getAll() {
        
    	List<Book> books = new ArrayList<>();
    	
    	Connection conn = currentSession.getConnection();
    	
    	try {
    		
			CallableStatement cstmt = conn.prepareCall("{CALL rbs_app.get_all_books(?)}");
			
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			
			while(rs.next()) {
				Book temp = new Book();
				temp.setId(rs.getInt("book_id"));
				temp.setTitle(rs.getString("title"));
				temp.setIsbn(rs.getString("isbn"));
				temp.setAuthor(rs.getString("author"));
				temp.setGenre(rs.getString("genre"));
				temp.setStockCount(rs.getInt("stock_count"));
				books.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    	return books;
    }

    @Override
    public Book getById(int id) {
        return null;
    }

    @Override
    public Book add(Book newBook) {
        return null;
    }

    @Override
    public Book update(Book updatedBook) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

}
