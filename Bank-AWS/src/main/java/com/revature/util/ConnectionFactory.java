package com.revature.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.revature.models.User;
/**
 * @author Joe
 * ConnectionFactory read the database credential from the file build up the connection to de dadabase
 * */
public class ConnectionFactory {
	private static ConnectionFactory connFactory = new ConnectionFactory();

	private Properties props = new Properties();

	private ConnectionFactory() {
		super();

		try {

			props.load(new FileReader("src/main/resources/application.properties"));

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();

		} catch (IOException ioe) {
			ioe.printStackTrace();

		}
	}

	public static ConnectionFactory getInstance() {
		return connFactory;
	}

	/*
	 * obtains a connection to the database using the default DB user credentials to
	 * faciliate login and registration operations
	 * 
	 * @return conn
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			
			System.out.println("Attempting to obtain default connection...");
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

	/*
	 * obtains a connection to the database using the default DB user credentials to
	 * facilitate login and registration operations, basic create /read/update
	 * 
	 * 
	 * @param User sessionUser
	 * 
	 * @return connection conn
	 */
	public Connection getConnection(User sessionUser) {

		String userRole = sessionUser.getRole().getRoleName();
		
		Connection conn = null;
		try{
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
			
			
		}catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return conn;

	}

}
