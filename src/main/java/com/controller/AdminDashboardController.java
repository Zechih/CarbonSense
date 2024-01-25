package com.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

		// donnut pie
		CarbonReportAnalysis carbonReportAnalysis = new CarbonReportAnalysis();

		String waterSql = "SELECT SUM(WaterUsageValueM3) AS totalWaterConsumption FROM waterconsumption WHERE status = 'APPROVED'";
		try (ResultSet waterRs = conn.createStatement().executeQuery(waterSql)) {
			if (waterRs.next()) {
				carbonReportAnalysis.setTotalWaterCarbon(
						CarbonCalculation.calWaterCarbon(waterRs.getFloat("totalWaterConsumption")));
			}
		}

		String electricitySql = "SELECT SUM(ElectricUsageValueM3) AS totalElectricityConsumption FROM electricityconsumption WHERE status = 'APPROVED'";
		try (ResultSet electricityRs = conn.createStatement().executeQuery(electricitySql)) {
			if (electricityRs.next()) {
				carbonReportAnalysis.setTotalElectricityCarbon(
						CarbonCalculation.calElectricityCarbon(electricityRs.getFloat("totalElectricityConsumption")));
			}
		}

		String recycleSql = "SELECT SUM(RecycleKG) AS totalRecycle FROM recycle WHERE status = 'APPROVED'";
		try (ResultSet recycleRs = conn.createStatement().executeQuery(recycleSql)) {
			if (recycleRs.next()) {
				carbonReportAnalysis
						.setTotalRecycleCarbon(CarbonCalculation.calRecycleCarbon(recycleRs.getFloat("totalRecycle")));
			}
		}

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
				newRegion.setElectricity_Carbon(CarbonCalculation.calElectricityCarbon(regionRs.getFloat("totalElectricCon")));
				newRegion.setRecycle_Carbon(CarbonCalculation.calRecycleCarbon(regionRs.getFloat("totalRecycle")));
				newRegion.setTotal_Carbon(newRegion.getWater_Carbon() + newRegion.getElectricity_Carbon() + newRegion.getRecycle_Carbon());
				carbonRegionList.add(newRegion);
			}
		}
		
		model.addObject("carbonRegionList", carbonRegionList);
		return model;
	}
}
