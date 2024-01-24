package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dbUtil.DBConnect;
import com.model.Application;



@Controller
public class ReportController {
	@RequestMapping("/report")
	protected ModelAndView getReportPage() {
		ModelAndView model = new ModelAndView("report");
		return model;
	}
	
	@RequestMapping("/reportDetails")
	protected ModelAndView getReportDetailsPage(HttpServletRequest request) throws SQLException {
		
		ModelAndView model = new ModelAndView("reportDetails");
		Connection conn = DBConnect.openConnection();
		String sql = "SELECT * FROM application WHERE DATE_FORMAT(`date`, '%Y') = ? AND DATE_FORMAT(`date`, '%m') = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, Integer.parseInt(request.getParameter("year")));
		stmt.setInt(2, Integer.parseInt(request.getParameter("month")));
		ResultSet rs = stmt.executeQuery();
		
		ArrayList <Application> applicationList = new ArrayList<Application>();

	    while (rs.next()) {
	    	float totalEmission = 0;
	        Application application = new Application();
	        
	        String userSql = "SELECT FirstName, LastName FROM users WHERE UserID = " + rs.getInt("UserID");
	        ResultSet userRs = conn.createStatement().executeQuery(userSql);
	        while(userRs.next()) {
	        	application.setName(userRs.getString("FirstName")+ " " +userRs.getString("LastName"));
	        }
	        
	        String waterSql = "SELECT waterUsageValueM3 FROM waterConsumption WHERE WaterID = " + rs.getInt("WaterID");
	        ResultSet waterRs = conn.createStatement().executeQuery(waterSql);
	        while(waterRs.next()) {
	        	application.setWaterConsumption(waterRs.getFloat("waterUsageValueM3"));
	        	totalEmission = totalEmission + waterRs.getFloat("waterUsageValueM3");
	        }
	        
	        String electricitySql = "SELECT electricUsageValueM3 FROM electricityConsumption WHERE ElectricityID = " + rs.getInt("ElectricityID");
	        ResultSet electricityRs = conn.createStatement().executeQuery(electricitySql);
	        while(electricityRs.next()) {
	        	application.setElectricityConsumption(electricityRs.getFloat("electricUsageValueM3"));
	        	totalEmission = totalEmission + electricityRs.getFloat("electricUsageValueM3");
	        }
	        
	        String recycleSql = "SELECT recycleKG FROM recycle WHERE RecycleID = " + rs.getInt("RecycleID");
	        ResultSet recycleRs = conn.createStatement().executeQuery(recycleSql);
	        while(recycleRs.next()) {
	        	application.setRecycle(recycleRs.getFloat("recycleKG"));
	        	totalEmission = totalEmission + recycleRs.getFloat("recycleKG");
	        }

	        application.setCarbonEmission(totalEmission);
	        
	        applicationList.add(application);
	    }
	    
	    model.addObject("applicationList", applicationList);
		
		return model;
	}
	
	@RequestMapping("/dashboardAdmin")
	protected ModelAndView getDashboardAdminPage() {
		ModelAndView model = new ModelAndView("dashboardAdmin");
		return model;
	}
}
