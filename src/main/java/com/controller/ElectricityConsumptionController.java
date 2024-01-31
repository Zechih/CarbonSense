package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dbUtil.DBConnect;
import com.dbUtil.ElectricConDAO;

@Controller
public class ElectricityConsumptionController {
	@RequestMapping("/electricityConsumption")
	protected ModelAndView getElectricityConsumptionPage() throws SQLException {
		ModelAndView model = new ModelAndView("electricityConsumption");
		return model;
	}

	@RequestMapping("/electricitySubmit")
	protected ModelAndView getElectricitySubmitPage(@RequestParam("userID") int userID,
			@RequestParam("proportionalFactor") float proportionalFactor,
			@RequestParam("electricityUsageRM") float electricityUsageRM,
			@RequestParam("electricityUsageM3") float electricityUsageM3, @RequestParam MultipartFile billImage)
			throws SQLException, IOException {

		Connection conn = DBConnect.openConnection();
		LocalDate currentDate = LocalDate.now();
		String ApplicationSql = "SELECT * FROM application WHERE userID = ? AND DATE_FORMAT(`date`, '%Y') = ? AND DATE_FORMAT(`date`, '%m') = ?";
		PreparedStatement stmt = conn.prepareStatement(ApplicationSql);
		stmt.setInt(1, userID);
		stmt.setInt(2, currentDate.getYear());
		stmt.setInt(3, currentDate.getMonthValue());

		byte[] fileBytes = null;
		if (!billImage.isEmpty()) {
			fileBytes = billImage.getBytes();
		}

		String message = null;

		try (ResultSet rs = stmt.executeQuery()) {
			// if application have been made
			if (rs.next()) {
				// check whether the waterID is true
				if (rs.getInt("electricityID") > 0) {
					// have waterID update
					ElectricConDAO electricConDAO = new ElectricConDAO();
					electricConDAO.updateElectricCon(proportionalFactor, electricityUsageRM, electricityUsageM3,
							fileBytes, rs.getInt("electricityID"));
					message = "Update successfully";

				} else {
					// waterID not found insert
					ElectricConDAO electricConDAO = new ElectricConDAO();
					electricConDAO.insertElectricConAndUpdateApplication(proportionalFactor, electricityUsageRM,
							electricityUsageM3, fileBytes, rs.getInt("applicationID"));
					message = "Submit successfully";
				}

			} else {
				// create electricity consumption
				ElectricConDAO electricConDAO = new ElectricConDAO();
				electricConDAO.insertElectricConAndCreateApplication(proportionalFactor, electricityUsageRM,
						electricityUsageM3, fileBytes, userID, currentDate);
				message = "Submit successfully";
			}
		}

		ModelAndView model = new ModelAndView("electricitySubmitResponse");
		model.addObject("message", message);
		return model;
	}
}
