package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.util.ConnectionFactory;

public class TestDriver {

	public static void main(String[] args) {

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			System.out.println("test, connection succesful, print someting!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
