package com.revature.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import oracle.jdbc.OracleTypes;

public class UserDAO implements DAO<User> {
	
	public User getUserByUsername(String username) {
		
		User user = null;
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * "
														  + "FROM reimburse_app.users "
														  + "JOIN reimburse_app.user_roles "
														  + "USING (user_role_id) "
														  + "WHERE username = ?");
			
			pstmt.setString(1, username);
			List<User> users = mapResultSet(pstmt.executeQuery());
			if(!users.isEmpty()) user = users.get(0);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	

	public User getUserByCredentials(String username, String password) {
		
		User user = null;
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			String sql = "SELECT * "
					   + "FROM reimburse_app.users "
					   + "JOIN reimburse_app.user_roles "
					   + "USING (user_role_id) "
					   + "WHERE username = ? AND password = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			List<User> users = mapResultSet(rs);
			if(!users.isEmpty()) user = users.get(0);
			
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return user;
	}

	@Override
	public List<User> getAll() {
		
		List<User> users = new ArrayList<>();
		
		try {	Connection conn = ConnectionFactory.getInstance().getConnection();	
			CallableStatement cstmt = conn.prepareCall("{CALL reimburse_app.get_all_users(?)}");
			
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			
			users = mapResultSet(rs);
			
			users.forEach(user -> user.setPassword("**********"));
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return users;
	}

	@Override
	public User getById(int id) {
		
		User user = null;
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * "
														  + "FROM reimburse_app.users "
														  + "JOIN reimburse_app.user_roles "
														  + "USING (user_role_id) "
														  + "WHERE user_id = ?");
			
			pstmt.setInt(1, id);
			List<User> users = mapResultSet(pstmt.executeQuery());
			if(!users.isEmpty()) user = users.get(0);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public User add(User newUser) {
		
		User user = null;
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO reimburse_app.users "
														  + "VALUES (0, ?, ?, ?, ?, ?, 2)", 
														  new String[] { "user_id" } );
			pstmt.setString(1, newUser.getUsername());
			pstmt.setString(2, newUser.getPassword());
			pstmt.setString(3, newUser.getFirstName());
			pstmt.setString(4, newUser.getLastName());
			pstmt.setString(5, newUser.getEmail());
			
			if(pstmt.executeUpdate() != 0) {
				
				ResultSet rs = pstmt.getGeneratedKeys();
				
				while(rs.next()) {
					newUser.setId(rs.getInt(1));
				}
				
				conn.commit();
				
				user = newUser;
				
			}
			
		} catch(SQLIntegrityConstraintViolationException sicve) {
			sicve.printStackTrace();
			System.out.println("Username already taken!");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return user;
	}

	@Override
	public User update(User updatedUser) {
		return null;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}
	
	private List<User> mapResultSet(ResultSet rs) throws SQLException {
		
		List<User> users = new ArrayList<>();
		
		while(rs.next()) {
			User temp = new User();
			temp.setId(rs.getInt("user_id"));
			temp.setUsername(rs.getString("username"));
			temp.setFirstName(rs.getString("user_first_name"));
			temp.setLastName(rs.getString("user_last_name"));
			temp.setEmail(rs.getString("user_email"));
			temp.setRole(new Role(rs.getString("user_role")));
			users.add(temp);
		}
		
		return users;
	}

}