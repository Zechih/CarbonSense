package com.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dbUtil.AdminDashboardDAO;
import com.dbUtil.DBConnect;
import com.model.CarbonCalculation;
import com.model.CarbonRegion;
import com.model.CarbonReportAnalysis;

@Controller
public class AdminDashboardController {
	@RequestMapping("/dashboardAdmin")
	protected ModelAndView getDashboardAdminPage() throws SQLException {
		ModelAndView model = new ModelAndView("dashboardAdmin");
		Connection conn = DBConnect.openConnection();
		AdminDashboardDAO adminDashboardDAO = new AdminDashboardDAO();

		// donnut graph
		CarbonReportAnalysis carbonReportAnalysis = new CarbonReportAnalysis();

		carbonReportAnalysis
				.setTotalWaterCarbon(CarbonCalculation.calWaterCarbon(adminDashboardDAO.getTotalWaterConsumption()));

		carbonReportAnalysis.setTotalElectricityCarbon(
				CarbonCalculation.calElectricityCarbon(adminDashboardDAO.getTotalElectricityConsumption()));

		carbonReportAnalysis
				.setTotalRecycleCarbon(CarbonCalculation.calRecycleCarbon(adminDashboardDAO.getTotalRecycle()));

		carbonReportAnalysis.setTotalCarbonEmission(carbonReportAnalysis.getTotalWaterCarbon()
				+ carbonReportAnalysis.getTotalElectricityCarbon() + carbonReportAnalysis.getTotalRecycleCarbon());

		model.addObject("carbonReportAnalysis", carbonReportAnalysis);

		// graph bar
		ArrayList<CarbonRegion> carbonRegionList = new ArrayList<CarbonRegion>();
		String regionSql = "SELECT users.`Region`, SUM(waterConsumption.`WaterUsageValueM3`) as totalWaterCon, SUM(electricityConsumption.`ElectricUsageValueM3`) as totalElectricCon, SUM(recycle.`RecycleKG`) as totalRecycle FROM users LEFT JOIN application ON users.UserID = application.UserID LEFT JOIN waterConsumption ON application.WaterID = waterConsumption.WaterID LEFT JOIN electricityConsumption ON application.ElectricityID = electricityConsumption.ElectricityID LEFT JOIN recycle ON application.RecycleID = recycle.RecycleID WHERE COALESCE(waterConsumption.`Status`, electricityConsumption.`Status`, recycle.`Status`) = 'APPROVED' GROUP BY users.`Region`;";
		try (ResultSet regionRs = conn.createStatement().executeQuery(regionSql)) {
			while (regionRs.next()) {
				CarbonRegion newRegion = new CarbonRegion();
				newRegion.setRegion(regionRs.getString("Region"));
				newRegion.setWater_Carbon(CarbonCalculation.calWaterCarbon(regionRs.getFloat("totalWaterCon")));
				newRegion.setElectricity_Carbon(
						CarbonCalculation.calElectricityCarbon(regionRs.getFloat("totalElectricCon")));
				newRegion.setRecycle_Carbon(CarbonCalculation.calRecycleCarbon(regionRs.getFloat("totalRecycle")));
				newRegion.setTotal_Carbon(newRegion.getWater_Carbon() + newRegion.getElectricity_Carbon()
						+ newRegion.getRecycle_Carbon());
				carbonRegionList.add(newRegion);
			}
		}

		model.addObject("carbonRegionList", carbonRegionList);

		// total participant

		model.addObject("totalParticipant", adminDashboardDAO.getTotalParticipants());

		// total submission

		model.addObject("totalSubmission", adminDashboardDAO.getTotalSubmissions());

		return model;
	}
}
