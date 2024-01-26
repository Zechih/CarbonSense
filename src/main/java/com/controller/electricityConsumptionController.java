package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dbUtil.DBConnect;

@Controller
public class electricityConsumptionController {
	@RequestMapping("/electricityConsumption")
	protected ModelAndView getElectricityConsumptionPage() throws SQLException {
		ModelAndView model = new ModelAndView("electricityconsumption");
		return model;
	}

	@RequestMapping("/electricitySubmit")
	protected ModelAndView getElectricitySubmitPage(@RequestParam("userID") int userID,
			@RequestParam("proportionalFactor") float proportionalFactor,
			@RequestParam("electricityUsageRM") float electricityUsageRM,
			@RequestParam("electricityUsageM3") float electricityUsageM3, 
			@RequestParam("billImage") String billImage)
			throws SQLException {

		Connection conn = DBConnect.openConnection();
		LocalDate currentDate = LocalDate.now();
		String ApplicationSql = "SELECT * FROM application WHERE userID = ? AND DATE_FORMAT(`date`, '%Y') = ? AND DATE_FORMAT(`date`, '%m') = ?";
		PreparedStatement stmt = conn.prepareStatement(ApplicationSql);
		stmt.setInt(1, userID);
		stmt.setInt(2, currentDate.getYear());
		stmt.setInt(3, currentDate.getMonthValue());
		try (ResultSet rs = stmt.executeQuery()) {
			// if application have been made
			if (rs.next()) {
				// check whether the waterID is true
				if (rs.getInt("waterID") > 0) {
					// have waterID update
					String updateWaterSql = "UPDATE waterconsumption SET WaterProportionalFactor = ?, WaterUsageValueRM = ?, WaterUsageValueM3 = ?, WaterConsumptionProof = ?, status = 'DISAPPROVED' WHERE waterID = ?;";
					PreparedStatement waterStmt = conn.prepareStatement(updateWaterSql);
					waterStmt.setFloat(1, proportionalFactor);
					waterStmt.setFloat(2, electricityUsageRM);
					waterStmt.setFloat(3, electricityUsageM3);
					waterStmt.setString(4, billImage);
					waterStmt.setInt(5, rs.getInt("waterID"));
					int affectedRows = waterStmt.executeUpdate();

				} else {
					// waterID not found insert
					String insertWaterSql = "INSERT INTO waterconsumption (WaterProportionalFactor, WaterUsageValueRM, WaterUsageValueM3, WaterConsumptionProof, status) VALUES (?, ?, ?, ?, 'DISAPPROVED');";
					PreparedStatement waterStmt = conn.prepareStatement(insertWaterSql,
							PreparedStatement.RETURN_GENERATED_KEYS);
					waterStmt.setFloat(1, proportionalFactor);
					waterStmt.setFloat(2, electricityUsageRM);
					waterStmt.setFloat(3, electricityUsageM3);
					waterStmt.setString(4, billImage);
					int affectedRows = waterStmt.executeUpdate();

					if (affectedRows > 0) {
						try (ResultSet generatedKeys = waterStmt.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								int waterID = generatedKeys.getInt(1);
								String updateApplicationSql = "UPDATE application SET waterID = ? WHERE applicationID = ?";
								try (PreparedStatement updateApplicationStmt = conn
										.prepareStatement(updateApplicationSql)) {
									updateApplicationStmt.setInt(1, waterID);
									updateApplicationStmt.setInt(2, rs.getInt("applicationID"));

									int updateApplicationRows = updateApplicationStmt.executeUpdate();
								}
							}
						}
					}
				}

			} else {
				// create water consumption
				String insertWaterSql = "INSERT INTO waterconsumption (WaterProportionalFactor, WaterUsageValueRM, WaterUsageValueM3, WaterConsumptionProof, status) VALUES (?, ?, ?, ?, 'DISAPPROVED');";
				PreparedStatement waterStmt = conn.prepareStatement(insertWaterSql,
						PreparedStatement.RETURN_GENERATED_KEYS);
				waterStmt.setFloat(1, proportionalFactor);
				waterStmt.setFloat(2, electricityUsageRM);
				waterStmt.setFloat(3, electricityUsageM3);
				waterStmt.setString(4, billImage);
				int affectedRows = waterStmt.executeUpdate();

				// create application and add water consumption
				if (affectedRows > 0) {
					try (ResultSet generatedKeys = waterStmt.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							int waterID = generatedKeys.getInt(1);
							String insertApplicationSql = "INSERT INTO application (userID, date, waterID) VALUES (?, ?, ?)";
							try (PreparedStatement insertApplicationStmt = conn
									.prepareStatement(insertApplicationSql)) {
								insertApplicationStmt.setInt(1, userID);
								insertApplicationStmt.setObject(2, currentDate);
								insertApplicationStmt.setInt(3, waterID);

								int insertApplicationRows = insertApplicationStmt.executeUpdate();
							}
						}
					}
				}

			}

		}

		ModelAndView model = new ModelAndView("electricityconsumption");
		return model;
	}
}
