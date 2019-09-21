package com.revature.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		
		try {
			props.load(new FileReader("src/main/resources/application.properties"));
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
			
			conn = DriverManager.getConnection(
				props.getProperty("url"),
				props.getProperty("ro-usr"),
				props.getProperty("ro-pw")
			);
			
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
			case "DEV":
				conn = DriverManager.getConnection(
					props.getProperty("url"),
					props.getProperty("admin-usr"),
					props.getProperty("admin-pw")
				);
				break;
			case "USER":
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
