package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dbUtil.DBConnect;

@Controller
public class LoginController {

	@RequestMapping("/login")
	protected ModelAndView getLoginPage() {
		ModelAndView model = new ModelAndView("login");
		return model;
	}

	@RequestMapping("/authenticate")
	protected ModelAndView authenticateUser(HttpServletRequest request, HttpSession session) throws SQLException {
		ModelAndView model;
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// Assuming you have a UserDAO class for database interactions
		Connection conn = DBConnect.openConnection();
		String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			stmt.setString(2, password);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {

					session.setAttribute("email", email);
					session.setAttribute("userID", rs.getInt("userID"));
					session.setAttribute("name", rs.getString("firstName") +" "+ rs.getString("lastName"));
					session.setAttribute("role", rs.getString("role"));
					session.setAttribute("status", rs.getString("status"));

					if ("USER".equals(rs.getString("role"))) {
						model = new ModelAndView("redirect:/home");

					} else if ("ADMIN".equals(rs.getString("role"))) {
						model = new ModelAndView("redirect:/dashboardAdmin");
						
					} else {
						model = new ModelAndView("redirect:/login");
						model.addObject("error", "Invalid credentials. Please try again.");
						
					}

				} else {
					// Invalid credentials, redirect back to login page with an error message
					model = new ModelAndView("redirect:/login");
					model.addObject("error", "Invalid credentials. Please try again.");
				}
			}
		}

		return model;
	}

	@RequestMapping("/home")
	protected ModelAndView getHomePage(HttpSession session) {
		ModelAndView model = new ModelAndView("dashboard");

		String email = (String) session.getAttribute("email");

		model.addObject("email", email);

		return model;
	}

}
