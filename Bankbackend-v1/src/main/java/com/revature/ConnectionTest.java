package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.util.ConnectionFactory;

public class ConnectionTest {
	

	public static void main(String[] args) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()){
			if(conn != null) {
				System.out.println("Connection made ot DB!");
			} else
				System.out.println("You screwed up .......");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

	}

}
