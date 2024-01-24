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
import com.model.CarbonCalculation;
import com.model.CarbonReportAnalysis;

@Controller
public class ReportController {
	@RequestMapping("/report")
	protected ModelAndView getReportPage() throws SQLException {
		ModelAndView model = new ModelAndView("report");
		Connection conn = DBConnect.openConnection();
		String sql = "SELECT MIN(DATE_FORMAT(`date`, '%Y')) AS smallest_year, MIN(DATE_FORMAT(`date`, '%m')) AS smallest_month FROM application;";
		ResultSet rs = conn.createStatement().executeQuery(sql);
		
		if (rs.next()) {
			model.addObject("smallest_year", rs.getInt("smallest_year"));
			model.addObject("smallest_month", rs.getInt("smallest_month"));
			conn.close();
		}
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
		float totalWaterCarbon = 0;
		float totalElectricityCarbon = 0;
		float totalRecycleCarbon = 0;
		float totalCarbonEmission = 0;

		ArrayList<Application> applicationList = new ArrayList<Application>();
		CarbonReportAnalysis carbonReportAnalysis = new CarbonReportAnalysis();

		while (rs.next()) {
			float totalEmission = 0;
			Application application = new Application();

			String userSql = "SELECT FirstName, LastName FROM users WHERE UserID = " + rs.getInt("UserID");
			ResultSet userRs = conn.createStatement().executeQuery(userSql);
			while (userRs.next()) {
				application.setName(userRs.getString("FirstName") + " " + userRs.getString("LastName"));
			}

			String waterSql = "SELECT waterUsageValueM3 FROM waterConsumption WHERE WaterID = " + rs.getInt("WaterID");
			ResultSet waterRs = conn.createStatement().executeQuery(waterSql);
			while (waterRs.next()) {
				application.setWaterConsumption(waterRs.getFloat("waterUsageValueM3"));
				totalEmission = totalEmission + CarbonCalculation.calWaterCarbon(waterRs.getFloat("waterUsageValueM3"));
				totalWaterCarbon = totalWaterCarbon
						+ CarbonCalculation.calWaterCarbon(waterRs.getFloat("waterUsageValueM3"));
			}

			String electricitySql = "SELECT electricUsageValueM3 FROM electricityConsumption WHERE ElectricityID = "
					+ rs.getInt("ElectricityID");
			ResultSet electricityRs = conn.createStatement().executeQuery(electricitySql);
			while (electricityRs.next()) {
				application.setElectricityConsumption(electricityRs.getFloat("electricUsageValueM3"));
				totalEmission = totalEmission
						+ CarbonCalculation.calElectricityCarbon(electricityRs.getFloat("electricUsageValueM3"));
				totalElectricityCarbon = totalElectricityCarbon
						+ CarbonCalculation.calElectricityCarbon(electricityRs.getFloat("electricUsageValueM3"));
			}

			String recycleSql = "SELECT recycleKG FROM recycle WHERE RecycleID = " + rs.getInt("RecycleID");
			ResultSet recycleRs = conn.createStatement().executeQuery(recycleSql);
			while (recycleRs.next()) {
				application.setRecycle(recycleRs.getFloat("recycleKG"));
				totalEmission = totalEmission + CarbonCalculation.calRecycleCarbon(recycleRs.getFloat("recycleKG"));
				totalRecycleCarbon = totalRecycleCarbon
						+ CarbonCalculation.calRecycleCarbon(recycleRs.getFloat("recycleKG"));
			}

			totalCarbonEmission = totalCarbonEmission + totalEmission;
			application.setCarbonEmission(totalEmission);

			if (totalEmission != 0) {
				applicationList.add(application);
			}
		}

		conn.close();
		carbonReportAnalysis.setTotalWaterCarbon(totalWaterCarbon);
		carbonReportAnalysis.setTotalElectricityCarbon(totalElectricityCarbon);
		carbonReportAnalysis.setTotalRecycleCarbon(totalRecycleCarbon);
		carbonReportAnalysis.setTotalCarbonEmission(totalCarbonEmission);

		model.addObject("applicationList", applicationList);
		model.addObject("carbonReportAnalysis", carbonReportAnalysis);

		return model;
	}

	@RequestMapping("/dashboardAdmin")
	protected ModelAndView getDashboardAdminPage() {
		ModelAndView model = new ModelAndView("dashboardAdmin");
		return model;
	}
}
