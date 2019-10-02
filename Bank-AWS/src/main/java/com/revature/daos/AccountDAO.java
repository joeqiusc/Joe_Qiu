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

/**
// * @author Joe
 * The class implements the DAO interface, will communication with the database and the account service.
 * once user login or register as a new user, the user can through this class
 * to access the account information in the database.
 *
 */
public class AccountDAO implements DAO<Account> {

	/**
	 * 
	 *@author Joe
	 *@param newAccount
	 *@return account
	 * 
	 */
	public Account add(Account newAccount) {
		Account account = null;
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO mockbank.account values (0, ?, ?)",
					new String[] { "account_no" });
			pstmt.setInt(1, newAccount.getUserId());
			pstmt.setDouble(2, 0);

			if (pstmt.executeUpdate() != 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {// Here we generate the new register user an account No.
					newAccount.setAccountNo(rs.getInt(1));
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
	/**
	 *This method will access the database, update the the account information
	 * @author Joe
	 * @param account
	 * @return account
	 * @exception if the connection to the database fail or the query information doesn't match
	 * the database, it will throw the sql exception.
	 * @Override
	 * */
	
	public Account update(Account account) {

		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("UPDATE mockbank.account "
															+ "SET balance=? "
															+ "WHERE user_id = ?");
			
			pstmt.setDouble(1, account.getBalance());
			pstmt.setInt(2, account.getUserId());
			pstmt.executeUpdate();
//			System.out.println("test print after update!");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
			
		return account;
	}
	
	
	/**
	 * @Override
	 * @param int
	 * @return account
	 * @exception if the connection to the database fail or the query information doesn't match
	 * the database, it will throw the sql exception.
	 * 
	 * */
	public Account getById(int id) {

		Account account = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("SELECT users.user_id, users.First_Name || ' ' || users.Last_Name As Name, "
					                                            + "account.balance "
																+ "from mockbank.users users " 
																+	"join mockbank.account account " 
																+	"on users.user_id = account.user_id  " 
																+	"where users.user_id=?");
			pstmt.setInt(1, id);
			List<Account> accounts = mapResultSet(pstmt.executeQuery());
			if(!accounts.isEmpty()) account = accounts.get(0);							
		} catch (SQLException sqle) {
			
			sqle.printStackTrace();
		}
		
		return account;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	

/**
 * For Project-0, we don't need this method, because current, I don't want to implement the admin
 * function, while in the future, if I have time, I need this function, I will implement it.
 * Then the administrator can access it, there could be some function to unlock some locked user.
 * @param no parameter need in this class
 * @return it will return all account information, it only for the bank use, not for the customers
 * @exception if the connection to the database fail or the query information doesn't match
 * the database, it will throw the sql exception.
 * 
 * */  
	@Override
	public List<Account> getAll() {

		List<Account> accounts = new ArrayList<>();

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
				accounts.add(temp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return accounts;

	}
	/**
	 * @param resultset object
	 * @exception if the connection to the database fail or the query information doesn't match
	 * the database, it will throw the sql exception.
	 * @return this method will return the query result into a list.
	 * */
	
	private List<Account> mapResultSet(ResultSet rs) throws SQLException {
		
		List<Account> accounts = new ArrayList<>();		
		while(rs.next()) {
			Account temp = new Account();
			if(rs.getObject("user_id") != null) {
				temp.setUserId(rs.getInt("user_id"));
			}
			if(rs.getObject("balance") != null) {
				temp.setBalance(rs.getDouble("balance"));
			}

			accounts.add(temp);
		}	
		return accounts;
	}

}
