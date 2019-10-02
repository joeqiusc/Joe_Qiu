package com.revature.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement_status;
import com.revature.util.ConnectionFactory;

import oracle.jdbc.OracleTypes;


public class Reimbursement_statusDAO implements DAO<Reimbursement_status> {

	@Override
	public List<Reimbursement_status> getAll() {

		Connection conn = ConnectionFactory.getInstance().getConnection();
		List<Reimbursement_status> reimbursement_status = new ArrayList<>();

		try {
			CallableStatement cstmt = conn.prepareCall("{CALL reimburse_app.get_all_reimburesment_status(?)}");

			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			reimbursement_status = mapResultSet(rs);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return reimbursement_status;
	}

	@Override
	public Reimbursement_status getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reimbursement_status add(Reimbursement_status newReimbursement_status) {
		Reimbursement_status reimbursement_status = null;
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			CallableStatement cstmt = conn.prepareCall("{CALL reimburse_app.insertReimbursement_status(?)}");

			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();

			cstmt.setInt(1, newReimbursement_status.getReimb_status_id());
			cstmt.setString(2, newReimbursement_status.getReimb_status());
		} catch (SQLIntegrityConstraintViolationException sicve) {
			sicve.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return reimbursement_status;
	}


	@Override
	public Reimbursement_status update(Reimbursement_status reimbursement_status) {

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE reimburse_app.reimbursement_status " + "SET reimb_status? " + "WHERE reimb_status_id= ?");

			pstmt.setString(1, reimbursement_status.getReimb_status());
			pstmt.setInt(2, reimbursement_status.getReimb_status_id());

		} catch (SQLIntegrityConstraintViolationException sicve) {
			sicve.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return reimbursement_status;
	}

	private List<Reimbursement_status> mapResultSet(ResultSet rs) throws SQLException {

		List<Reimbursement_status> reimbursement_status = new ArrayList<>();

		while (rs.next()) {
			Reimbursement_status temp = new Reimbursement_status();
			temp.setReimb_status_id(rs.getInt("reimb_status_id"));
			temp.setReimb_status(rs.getString("reimb_status"));
			reimbursement_status.add(temp);
		}

		return reimbursement_status;
	}
	
	
	
	

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
