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
import com.dbUtil.ElectricValidateDAO;
import com.model.ElectricityValidation;

@Controller
public class ElectricityValidationController {
	@RequestMapping("/electricityValidation")
	protected ModelAndView getElectricityValidationPage() throws SQLException {
		
		ModelAndView model = new ModelAndView("electricityValidation");
		ArrayList<ElectricityValidation> electricityValidationList = new ArrayList<ElectricityValidation>();
		Connection conn = DBConnect.openConnection();
		String sql= "SELECT * FROM electricityconsumption WHERE status ='PENDING';";
		try(ResultSet rs = conn.createStatement().executeQuery(sql)){
			while(rs.next()) {
				ElectricityValidation electricityValidation = new ElectricityValidation();
				electricityValidation.setElectricityID(rs.getInt("electricityID"));
				electricityValidation.setElectricityProportionalFactor(rs.getFloat("electricityProportionalFactor"));
				electricityValidation.setElectricUsageValueRM(rs.getFloat("electricUsageValueRM"));
				electricityValidation.setElectricUsageValueM3(rs.getFloat("electricUsageValueM3"));
				electricityValidation.setElectricConsumptionProof(rs.getBytes("electricConsumptionProof"));
				electricityValidation.setStatus(rs.getString("status"));
				electricityValidationList.add(electricityValidation);
			}
		}
		
		model.addObject("electricityValidationList", electricityValidationList);
		return model;
	}
	
	@RequestMapping("/electricityValidationApprove")
	protected ModelAndView electricityValidationApprove(@RequestParam("electricityID") int electricityID) throws SQLException {
		
		ElectricValidateDAO electricValidateDAO = new ElectricValidateDAO();
		electricValidateDAO.approveElectric(electricityID);
		
		ModelAndView model = new ModelAndView("electricityValidationResponse");
		model.addObject("message", "Approve successfully");
		return model;
	}
	
	@RequestMapping("/electricityValidationDisapprove")
	protected ModelAndView electricityValidationDisapprove(@RequestParam("electricityID") int electricityID) throws SQLException {
		
		ElectricValidateDAO electricValidateDAO = new ElectricValidateDAO();
		electricValidateDAO.disapproveElectric(electricityID);
		
		ModelAndView model = new ModelAndView("electricityValidationResponse");
		model.addObject("message", "Disapprove successfully");
		return model;
	}
	
	@RequestMapping("/electricityValidationDelete")
	protected ModelAndView electricityValidationDelete(@RequestParam("electricityID") int electricityID) throws SQLException {
		
		ElectricValidateDAO electricValidateDAO = new ElectricValidateDAO();
		electricValidateDAO.deleteElectric(electricityID);
		
		ModelAndView model = new ModelAndView("electricityValidationResponse");
		model.addObject("message", "Delete successfully");
		return model;
	}
}
