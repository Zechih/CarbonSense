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
	        Application application = new Application();
	        application.setName(rs.getString("UserID"));
	        application.setWaterConsumption(rs.getFloat("waterUsageValueM3"));
	        application.setElectricityConsumption(rs.getFloat("electricUsageValueM3"));
	        application.setRecycle(rs.getFloat("recycleKG"));
	        application.setCarbonEmission(100);
	        
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
