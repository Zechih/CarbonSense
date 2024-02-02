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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.dbUtil.DBConnect;
import com.dbUtil.ElectricConDAO;
import com.model.ElectricityValidation;

@Controller
public class ElectricityConsumptionController {
	@RequestMapping("/electricityConsumption")
	protected ModelAndView getElectricityConsumptionPage(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		int userID = (Integer) session.getAttribute("userID");
		try (Connection conn = DBConnect.openConnection()) {
			LocalDate currentDate = LocalDate.now();
			String sql = "SELECT * FROM application WHERE userID = ? AND DATE_FORMAT(`date`, '%Y') = ? AND DATE_FORMAT(`date`, '%m') = ?";

			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, userID);
				stmt.setInt(2, currentDate.getYear());
				stmt.setInt(3, currentDate.getMonthValue());

				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						if (rs.getInt("electricityID") > 0) {
							// Electricity consumption already submitted
							ElectricConDAO electricConDAO = new ElectricConDAO();
							ElectricityValidation electricityCon = electricConDAO
									.getElectricConDetails(rs.getInt("electricityID"));
							ModelAndView model = new ModelAndView("electricityConsumption");
							model.addObject("userID", userID);
							model.addObject("electricityCon", electricityCon);
							return model;
						} else {
							// Redirect to submission form
							ModelAndView model = new ModelAndView(new RedirectView("electricityConsumptionForm"));
							model.setViewName("redirect:/electricityConsumptionForm");
							return model;
						}
					}
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		ModelAndView model = new ModelAndView(new RedirectView("electricityConsumptionForm"));
		model.setViewName("redirect:/electricityConsumptionForm");
		return model;
	}

	@RequestMapping("/electricityConsumptionForm")
	protected ModelAndView getElectricityConsumptionFormPage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("electricityConsumptionForm");
		HttpSession session = request.getSession();
		model.addObject("userID", (Integer) session.getAttribute("userID"));
		return model;
	}

	@RequestMapping("/electricityConsumptionEdit")
	protected ModelAndView getElectricityConsumptionEditPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int userID = (Integer) session.getAttribute("userID");
		try (Connection conn = DBConnect.openConnection()) {
			LocalDate currentDate = LocalDate.now();
			String sql = "SELECT * FROM application WHERE userID = ? AND DATE_FORMAT(`date`, '%Y') = ? AND DATE_FORMAT(`date`, '%m') = ?";

			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, userID);
				stmt.setInt(2, currentDate.getYear());
				stmt.setInt(3, currentDate.getMonthValue());

				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						if (rs.getInt("electricityID") > 0) {
							// Electricity consumption already submitted
							ElectricConDAO electricConDAO = new ElectricConDAO();
							ElectricityValidation electricityCon = electricConDAO
									.getElectricConDetails(rs.getInt("electricityID"));
							ModelAndView model = new ModelAndView("electricityConsumptionEdit");
							model.addObject("userID", userID);
							model.addObject("electricityCon", electricityCon);
							return model;
						} else {
							// Redirect to submission form
							ModelAndView model = new ModelAndView(new RedirectView("electricityConsumptionForm"));
							model.setViewName("redirect:/electricityConsumptionForm");
							return model;
						}
					}
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		ModelAndView model = new ModelAndView(new RedirectView("electricityConsumptionForm"));
		model.setViewName("redirect:/electricityConsumptionForm");
		return model;
	}

	@RequestMapping("/electricitySubmit")
	protected ModelAndView getElectricitySubmitPage(HttpServletRequest request,
			@RequestParam("proportionalFactor") float proportionalFactor,
			@RequestParam("electricityUsageRM") float electricityUsageRM,
			@RequestParam("electricityUsageM3") float electricityUsageM3, @RequestParam MultipartFile billImage)
			throws SQLException, IOException {
		
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
			// if application have been made
			if (rs.next()) {
				// check whether the waterID is true
				if (rs.getInt("electricityID") > 0) {
					// have waterID update
					ElectricConDAO electricConDAO = new ElectricConDAO();
					if (fileBytes != null) {
						electricConDAO.updateElectricCon(proportionalFactor, electricityUsageRM, electricityUsageM3,
								fileBytes, rs.getInt("electricityID"));
						message = "Update successfully";
					} else {
						electricConDAO.updateElectricConNoProof(proportionalFactor, electricityUsageRM,
								electricityUsageM3, rs.getInt("electricityID"));
						message = "Update successfully";
					}

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
