package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dbUtil.DBConnect;
import com.model.RecycleValidation;

@Controller
public class RecycleValidationController {
	@RequestMapping("/recycleValidation")
	protected ModelAndView getRecycleValidationPage() throws SQLException {
		
		ModelAndView model = new ModelAndView("recycleValidation");
		ArrayList<RecycleValidation> recycleValidationList = new ArrayList<RecycleValidation>();
		Connection conn = DBConnect.openConnection();
		String sql= "SELECT * FROM recycle WHERE status ='PENDING';";
		try(ResultSet rs = conn.createStatement().executeQuery(sql)){
			while(rs.next()) {
				RecycleValidation recycleValidation = new RecycleValidation();
				recycleValidation.setRecycleID(rs.getInt("recycleID"));
				recycleValidation.setAccumulatedKg(rs.getFloat("recycleKG"));
				recycleValidation.setRecycleRM(rs.getFloat("recycleRM"));
				recycleValidation.setRecycleConsumptionProof(rs.getBytes("recycleProof"));
				recycleValidation.setStatus(rs.getString("status"));
				recycleValidationList.add(recycleValidation);
			}
		}
		
		model.addObject("recycleValidationList", recycleValidationList);
		return model;
	}
	
	@RequestMapping("/recycleValidationApprove")
	protected ModelAndView recycleValidationApprove(@RequestParam("recycleID") int recycleID) throws SQLException {
		
		Connection conn = DBConnect.openConnection();
		String approveSql = "UPDATE recycle SET status = 'APPROVED' WHERE recycleID = ?;";
		PreparedStatement stmt = conn.prepareStatement(approveSql);
		stmt.setInt(1, recycleID);
		int rs = stmt.executeUpdate();
		
		ModelAndView model = new ModelAndView("recycleValidationResponse");
		model.addObject("message", "Approve successfully");
		return model;
	}
	
	@RequestMapping("/recycleValidationDelete")
	protected ModelAndView recycleValidationDelete(@RequestParam("recycleID") int recycleID) throws SQLException {
		
		Connection conn = DBConnect.openConnection();
		String updateSql = "UPDATE application SET recycleID = NULL WHERE recycleID = ?;";
		PreparedStatement updateStmt = conn.prepareStatement(updateSql);
		updateStmt.setInt(1, recycleID);
		int updateRs = updateStmt.executeUpdate();
		
		String deleteSql = "DELETE FROM recycle WHERE recycleID = ?;";
		PreparedStatement stmt = conn.prepareStatement(deleteSql);
		stmt.setInt(1, recycleID);
		int rs = stmt.executeUpdate();
		
		ModelAndView model = new ModelAndView("recycleValidationResponse");
		model.addObject("message", "Delete successfully");
		return model;
	}
}

