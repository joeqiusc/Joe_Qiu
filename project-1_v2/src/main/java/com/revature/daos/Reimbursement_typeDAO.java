package com.revature.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement_type;
import com.revature.util.ConnectionFactory;

import oracle.jdbc.OracleTypes;

public class Reimbursement_typeDAO implements DAO<Reimbursement_type>{

	@Override
	public List<Reimbursement_type> getAll() {

		Connection conn = ConnectionFactory.getInstance().getConnection();
		List<Reimbursement_type> reimbursement_type = new ArrayList<>();

		try {
			CallableStatement cstmt = conn.prepareCall("{CALL reimburse_app.get_all_reimburesment_type(?)}");

			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			reimbursement_type = mapResultSet(rs);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return reimbursement_type;
	}

	@Override
	public Reimbursement_type getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reimbursement_type add(Reimbursement_type newReimbursement_type) {
		Reimbursement_type reimbursement_type = null;
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			CallableStatement cstmt = conn.prepareCall("{CALL reimburse_app.insertReimbursement_type(?)}");

			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();

			cstmt.setInt(1, newReimbursement_type.getReimb_type_id());
			cstmt.setString(2, newReimbursement_type.getReimb_type());
		} catch (SQLIntegrityConstraintViolationException sicve) {
			sicve.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return reimbursement_type;
	}

	private List<Reimbursement_type> mapResultSet(ResultSet rs) throws SQLException {

		List<Reimbursement_type> reimbursement_type = new ArrayList<>();

		while (rs.next()) {
			Reimbursement_type temp = new Reimbursement_type();
			temp.setReimb_type_id(rs.getInt("reimb_type_id"));
			temp.setReimb_type(rs.getString("reimb_type"));
			reimbursement_type.add(temp);
		}

		return reimbursement_type;
	}
	
	@Override
	public Reimbursement_type update(Reimbursement_type updateOb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
