package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dbUtil.DBConnect;

@Controller()
public class RecycleConsumptionController {
	@RequestMapping("/recycleConsumption")
	public ModelAndView getRecycleConsumptionPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int userID = (Integer) session.getAttribute("userID");
		ModelAndView mav = new ModelAndView("recycleConsumption");
		return mav;
	}

	@RequestMapping("/recycleSubmit")
	protected ModelAndView getElectricitySubmitPage(HttpServletRequest request,
			@RequestParam("AccumulatedKg") float AccumulatedKg, @RequestParam("recycleRM") float recycleRM,
			@RequestParam MultipartFile billImage) throws SQLException, IOException {

		HttpSession session = request.getSession();
		int userID = (Integer) session.getAttribute("userID");
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

			if (rs.next()) {

				if (rs.getInt("recycleID") > 0) {

					String updateRecycleSql = "UPDATE recycle SET recycleKG = ?, recycleRM = ?, recycleProof = ?, status = 'PENDING' WHERE recycleID = ?;";
					PreparedStatement recycleStmt = conn.prepareStatement(updateRecycleSql);
					recycleStmt.setFloat(1, AccumulatedKg);
					recycleStmt.setFloat(2, recycleRM);
					recycleStmt.setBytes(3, fileBytes);
					recycleStmt.setInt(4, rs.getInt("recycleID"));
					int affectedRows = recycleStmt.executeUpdate();
					message = "Update successfully";

				} else {
					// waterID not found insert
					String insertRecycleSql = "INSERT INTO recycle (recycleKG, recycleRM, recycleProof, status) VALUES (?, ?, ?, 'PENDING');";
					PreparedStatement recycleStmt = conn.prepareStatement(insertRecycleSql,
							PreparedStatement.RETURN_GENERATED_KEYS);
					recycleStmt.setFloat(1, AccumulatedKg);
					recycleStmt.setFloat(2, recycleRM);
					recycleStmt.setBytes(3, fileBytes);
					int affectedRows = recycleStmt.executeUpdate();

					if (affectedRows > 0) {
						try (ResultSet generatedKeys = recycleStmt.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								int recycleID = generatedKeys.getInt(1);
								String updateApplicationSql = "UPDATE application SET recycleID = ? WHERE applicationID = ?";
								try (PreparedStatement updateApplicationStmt = conn
										.prepareStatement(updateApplicationSql)) {
									updateApplicationStmt.setInt(1, recycleID);
									updateApplicationStmt.setInt(2, rs.getInt("applicationID"));

									int updateApplicationRows = updateApplicationStmt.executeUpdate();
									message = "Submit successfully";
								}
							}
						}
					}
				}

			} else {
				String insertRecycleSql = "INSERT INTO recycle (recycleKg, recycleRM, recycleProof, status) VALUES (?, ?, ?, 'PENDING');";
				PreparedStatement recycleStmt = conn.prepareStatement(insertRecycleSql,
						PreparedStatement.RETURN_GENERATED_KEYS);
				recycleStmt.setFloat(1, AccumulatedKg);
				recycleStmt.setFloat(2, recycleRM);
				recycleStmt.setBytes(3, fileBytes);
				int affectedRows = recycleStmt.executeUpdate();

				// create application and add electricity consumption
				if (affectedRows > 0) {
					try (ResultSet generatedKeys = recycleStmt.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							int recycleID = generatedKeys.getInt(1);
							String insertApplicationSql = "INSERT INTO application (userID, date, recycleID) VALUES (?, ?, ?)";
							try (PreparedStatement insertApplicationStmt = conn
									.prepareStatement(insertApplicationSql)) {
								insertApplicationStmt.setInt(1, userID);
								insertApplicationStmt.setObject(2, currentDate);
								insertApplicationStmt.setInt(3, recycleID);

								int insertApplicationRows = insertApplicationStmt.executeUpdate();
								message = "Submit successfully";
							}
						}
					}
				}

			}

		}

		ModelAndView model = new ModelAndView("recycleActivityResponse");
		model.addObject("message", message);
		return model;
	}

}
