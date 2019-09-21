package com.revature.daos;

import static com.revature.AppDriver.currentSession;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.util.ConnectionFactory;

import oracle.jdbc.OracleTypes;

public class AccountDAO implements DAO<Account> {

	@Override
	public List<Account> getAll() {

		List<Account> account = new ArrayList<>();

		Connection conn = currentSession.getConnection();

		try {
			CallableStatement cstmt = conn.prepareCall("{CALL mockbank.get_all_account(?)}");
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();

			ResultSet rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				Account temp = new Account();
				temp.setAccountNo(rs.getInt("account_no"));
				temp.setUserId(rs.getInt("user_id"));
				temp.setBalance(rs.getDouble("balance"));
				account.add(temp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return account;

	}

	public Account add(Account newAccount) {

		Account account = null;
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO mockbank.account " + " (0, ?, ?)",
					new String[] { "account_no" });
			pstmt.setInt(1, newAccount.getUserId());// here is the problem
			pstmt.setDouble(2, 0);

			if (pstmt.executeUpdate() != 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				while (rs.next()) {
					newAccount.setAccountNo(rs.getInt("account_no"));
				}
				conn.commit();
				account = newAccount;
			}
		} catch (SQLIntegrityConstraintViolationException sicve) {
			sicve.printStackTrace();
			System.out.println("Username already taken!");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return account;
	}
// This function I am not sure whether I need to connect to the database	
	public double updateBalance(double balance) {		
		return balance;
	}

	@Override
	public Account getById(int id) {

		return null;
	}

	@Override
	public Account update(Account updateOb) {
		
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
