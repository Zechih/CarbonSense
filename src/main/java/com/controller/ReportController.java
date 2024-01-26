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
import com.model.CarbonRegion;
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

	@RequestMapping("/reportError")
	protected ModelAndView getReportErrorPage() throws SQLException {
		ModelAndView model = new ModelAndView("reportError");
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
		ArrayList<CarbonRegion> carbonRegionList = new ArrayList<CarbonRegion>();
		CarbonReportAnalysis carbonReportAnalysis = new CarbonReportAnalysis();

		while (rs.next()) {
			float waterCarbon = 0;
			float electricityCarbon = 0;
			float recycleCarbon = 0;
			float totalEmission = 0;
			Application application = new Application();

			String userSql = "SELECT FirstName, LastName, Region, Category FROM users WHERE UserID = "
					+ rs.getInt("UserID");
			ResultSet userRs = conn.createStatement().executeQuery(userSql);
			if (userRs.next()) {
				application.setName(userRs.getString("FirstName") + " " + userRs.getString("LastName"));
				application.setCategory(userRs.getString("Category"));
				application.setRegion(userRs.getString("Region"));
			}

			String waterSql = "SELECT waterUsageValueM3 FROM waterConsumption WHERE WaterID = " + rs.getInt("WaterID") + " AND status = 'APPROVED'";
			ResultSet waterRs = conn.createStatement().executeQuery(waterSql);
			if (waterRs.next()) {
				waterCarbon = CarbonCalculation.calWaterCarbon(waterRs.getFloat("waterUsageValueM3"));
				application.setWaterConsumption(waterRs.getFloat("waterUsageValueM3"));
				totalEmission = totalEmission + waterCarbon;
				totalWaterCarbon = totalWaterCarbon + waterCarbon;
			}

			String electricitySql = "SELECT electricUsageValueM3 FROM electricityConsumption WHERE ElectricityID = "
					+ rs.getInt("ElectricityID") + " AND status = 'APPROVED'";
			ResultSet electricityRs = conn.createStatement().executeQuery(electricitySql);
			if (electricityRs.next()) {
				electricityCarbon = CarbonCalculation
						.calElectricityCarbon(electricityRs.getFloat("electricUsageValueM3"));
				application.setElectricityConsumption(electricityRs.getFloat("electricUsageValueM3"));
				totalEmission = totalEmission + electricityCarbon;
				totalElectricityCarbon = totalElectricityCarbon + electricityCarbon;
			}

			String recycleSql = "SELECT recycleKG FROM recycle WHERE RecycleID = " + rs.getInt("RecycleID") + " AND status = 'APPROVED'";
			ResultSet recycleRs = conn.createStatement().executeQuery(recycleSql);
			if (recycleRs.next()) {
				recycleCarbon = CarbonCalculation.calRecycleCarbon(recycleRs.getFloat("recycleKG"));
				application.setRecycle(recycleRs.getFloat("recycleKG"));
				totalEmission = totalEmission + recycleCarbon;
				totalRecycleCarbon = totalRecycleCarbon + recycleCarbon;
			}

			totalCarbonEmission = totalCarbonEmission + totalEmission;
			application.setCarbonEmission(totalEmission);

			if (totalEmission != 0) {
				applicationList.add(application);

				boolean regionInList = false;
				for (CarbonRegion carbonRegionLists : carbonRegionList) {
					if (carbonRegionLists.getRegion() == application.getRegion()) {
						regionInList = true;
						carbonRegionLists.setWater_Carbon(carbonRegionLists.getWater_Carbon() + waterCarbon);
						carbonRegionLists
								.setElectricity_Carbon(carbonRegionLists.getElectricity_Carbon() + electricityCarbon);
						carbonRegionLists.setRecycle_Carbon(carbonRegionLists.getRecycle_Carbon() + recycleCarbon);
						carbonRegionLists.setTotal_Carbon(carbonRegionLists.getTotal_Carbon() + totalEmission);
						break;
					}
				}

				if (!regionInList) {
					CarbonRegion newRegion = new CarbonRegion();
					newRegion.setRegion(application.getRegion());
					newRegion.setWater_Carbon(waterCarbon);
					newRegion.setElectricity_Carbon(electricityCarbon);
					newRegion.setRecycle_Carbon(recycleCarbon);
					newRegion.setTotal_Carbon(totalEmission);
					carbonRegionList.add(newRegion);
				}
			}
		}

		conn.close();
		carbonReportAnalysis.setTotalWaterCarbon(totalWaterCarbon);
		carbonReportAnalysis.setTotalElectricityCarbon(totalElectricityCarbon);
		carbonReportAnalysis.setTotalRecycleCarbon(totalRecycleCarbon);
		carbonReportAnalysis.setTotalCarbonEmission(totalCarbonEmission);
		if (totalCarbonEmission == 0) {
			return new ModelAndView("/reportError");
		}
		
		model.addObject("carbonRegionList", carbonRegionList);
		model.addObject("applicationList", applicationList);
		model.addObject("carbonReportAnalysis", carbonReportAnalysis);

		return model;
	}

}
