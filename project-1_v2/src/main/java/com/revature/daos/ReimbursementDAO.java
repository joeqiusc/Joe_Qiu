package com.revature.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.util.ConnectionFactory;

import oracle.jdbc.OracleTypes;

public class ReimbursementDAO implements DAO<Reimbursement>{
		
	

	@Override
	public List<Reimbursement> getAll() {
			
		Connection conn = ConnectionFactory.getInstance().getConnection();
			List<Reimbursement> reimbursements = new ArrayList<>();
			
			try {			
				CallableStatement cstmt = conn.prepareCall("{CALL reimburse_app.get_all_reimburesment(?)}");
				
				cstmt.registerOutParameter(1, OracleTypes.CURSOR);
				cstmt.execute();
				
				ResultSet rs = (ResultSet) cstmt.getObject(1);
				
				reimbursements = mapResultSet(rs);		
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			
			return reimbursements;
		}
	

	@Override
	public Reimbursement add(Reimbursement newReimbursement) {
		
		Reimbursement reimbursement = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			conn.setAutoCommit(false);
			
			CallableStatement cstmt = conn.prepareCall("{CALL reimburse_app.insertReimbursement(?)}");
			
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			
			cstmt.setInt(1, newReimbursement.getReimb_id() );
			cstmt.setDouble(2, newReimbursement.getReimb_amount());
			cstmt.setDate(3, newReimbursement.getReimb_submitted());
			cstmt.setDate(4, newReimbursement.getReimb_resolved());
			cstmt.setString(5, newReimbursement.getReimb_description());
			cstmt.setBlob(6, newReimbursement.getReimb_receipt());
			cstmt.setInt(7, newReimbursement.getReimb_author());
			cstmt.setInt(8, newReimbursement.getReimb_resolver());
			cstmt.setInt(9, newReimbursement.getReimb_status_id());
			cstmt.setInt(10, newReimbursement.getReimb_type_id());
			
			
		} catch (SQLIntegrityConstraintViolationException sicve) {
			sicve.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return reimbursement;
	}
	

	@Override
	public Reimbursement update(Reimbursement updateOb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Reimbursement getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Reimbursement> mapResultSet(ResultSet rs) throws SQLException {
		
		List<Reimbursement> reimbursements = new ArrayList<>();
		
		while(rs.next()) {
			Reimbursement temp = new Reimbursement();
			temp.setReimb_id(rs.getInt("reimb_id"));
			temp.setReimb_amount(rs.getDouble("reimb_amount"));
			temp.setReimb_submitted(rs.getDate("reimb_submitted"));
			temp.setReimb_resolved(rs.getDate("reimb_resolved"));
			temp.setReimb_description(rs.getString("reimb_description"));
			temp.setReimb_receipt(rs.getBlob("reimb_receipt"));
			temp.setReimb_author(rs.getInt("reimb_author"));
			temp.setReimb_resolver(rs.getInt("reimb_resolver"));
			temp.setReimb_status_id(rs.getInt("reimb_status_id"));
			temp.setReimb_type_id(rs.getInt("reimb_type_id"));
			reimbursements.add(temp);
		}
		
		return reimbursements;
	}

	

}
