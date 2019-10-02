package com.revature.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.revature.models.User;

public class ConnectionFactory {
	
	private static ConnectionFactory connFactory = new ConnectionFactory();
	
	private Properties props = new Properties();
	
	private ConnectionFactory() {
		super();
		
		// Fixes the issue where Tomcat cannot see beyond the "webapp" folder - causing FileNotFoundException for "application.properties"
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream input = loader.getResourceAsStream("application.properties");
		
		try {
			props.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ConnectionFactory getInstance() {
		return connFactory;
	}
	
	/**
	 * Obtains a connection to the database using the default DB user credentials, 
	 * to facilitate login and registration operations.
	 * 
	 * @return Connection conn
	 */
	public Connection getConnection() {
		
		
		Connection conn = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(
				props.getProperty("url"),
				props.getProperty("usr"),
				props.getProperty("pw")
			);
			
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * Obtains a connection to the database using the rbs_user credentials, 
	 * to facilitate standard user operations, basic create/read/update on
	 * certain tables.
	 * 
	 * @param User sessionUser
	 *		Represents the user who has logged into the RBS system
	 * 
	 * @return Connection conn
	 */
	public Connection getConnection(User sessionUser) {
		String userRole = sessionUser.getRole().getRoleName();
		Connection conn = null;
		
		try {
			switch(userRole) {
			case "ADMIN":
			
				conn = DriverManager.getConnection(
					props.getProperty("url"),
					props.getProperty("admin-usr"),
					props.getProperty("admin-pw")
				);
				break;
			case "user":
				conn = DriverManager.getConnection(
					props.getProperty("url"),
					props.getProperty("usr"),
					props.getProperty("pw")
				);
				break;
			default:
				conn = null;
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
		return conn;
	}

}
