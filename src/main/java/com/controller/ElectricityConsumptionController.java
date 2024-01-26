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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dbUtil.DBConnect;

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
			@RequestParam("electricityUsageM3") float electricityUsageM3, 
			@RequestParam MultipartFile billImage)
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
					String updateElectricitySql = "UPDATE electricityconsumption SET electricityProportionalFactor = ?, electricUsageValueRM = ?, electricUsageValueM3 = ?, electricConsumptionProof = ?, status = 'DISAPPROVED' WHERE electricityID = ?;";
					PreparedStatement electricityStmt = conn.prepareStatement(updateElectricitySql);
					electricityStmt.setFloat(1, proportionalFactor);
					electricityStmt.setFloat(2, electricityUsageRM);
					electricityStmt.setFloat(3, electricityUsageM3);
					electricityStmt.setBytes(4, fileBytes);
					electricityStmt.setInt(5, rs.getInt("electricityID"));
					int affectedRows = electricityStmt.executeUpdate();
					message = "Update successfully";

				} else {
					// waterID not found insert
					String insertElectricitySql = "INSERT INTO electricityconsumption (electricityProportionalFactor, electricUsageValueRM, electricUsageValueM3, electricConsumptionProof, status) VALUES (?, ?, ?, ?, 'DISAPPROVED');";
					PreparedStatement electricityStmt = conn.prepareStatement(insertElectricitySql,
							PreparedStatement.RETURN_GENERATED_KEYS);
					electricityStmt.setFloat(1, proportionalFactor);
					electricityStmt.setFloat(2, electricityUsageRM);
					electricityStmt.setFloat(3, electricityUsageM3);
					electricityStmt.setBytes(4, fileBytes);
					int affectedRows = electricityStmt.executeUpdate();

					if (affectedRows > 0) {
						try (ResultSet generatedKeys = electricityStmt.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								int electricityID = generatedKeys.getInt(1);
								String updateApplicationSql = "UPDATE application SET electricityID = ? WHERE applicationID = ?";
								try (PreparedStatement updateApplicationStmt = conn
										.prepareStatement(updateApplicationSql)) {
									updateApplicationStmt.setInt(1, electricityID);
									updateApplicationStmt.setInt(2, rs.getInt("applicationID"));

									int updateApplicationRows = updateApplicationStmt.executeUpdate();
									message = "Submit successfully";
								}
							}
						}
					}
				}

			} else {
				// create electricity consumption
				String insertElectricitySql = "INSERT INTO electricityconsumption (electricityProportionalFactor, electricUsageValueRM, electricUsageValueM3, electricConsumptionProof, status) VALUES (?, ?, ?, ?, 'DISAPPROVED');";
				PreparedStatement electricityStmt = conn.prepareStatement(insertElectricitySql,
						PreparedStatement.RETURN_GENERATED_KEYS);
				electricityStmt.setFloat(1, proportionalFactor);
				electricityStmt.setFloat(2, electricityUsageRM);
				electricityStmt.setFloat(3, electricityUsageM3);
				electricityStmt.setBytes(4, fileBytes);
				int affectedRows = electricityStmt.executeUpdate();

				// create application and add electricity consumption
				if (affectedRows > 0) {
					try (ResultSet generatedKeys = electricityStmt.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							int electricityID = generatedKeys.getInt(1);
							String insertApplicationSql = "INSERT INTO application (userID, date, electricityID) VALUES (?, ?, ?)";
							try (PreparedStatement insertApplicationStmt = conn
									.prepareStatement(insertApplicationSql)) {
								insertApplicationStmt.setInt(1, userID);
								insertApplicationStmt.setObject(2, currentDate);
								insertApplicationStmt.setInt(3, electricityID);

								int insertApplicationRows = insertApplicationStmt.executeUpdate();
								message = "Submit successfully";
							}
						}
					}
				}

			}

		}
		
		ModelAndView model = new ModelAndView("electricitySubmitResponse");
		model.addObject("message", message);
		return model;
	}
}
