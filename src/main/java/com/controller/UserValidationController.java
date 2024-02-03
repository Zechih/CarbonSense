package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dbUtil.DBConnect;
import com.dbUtil.UserValidateDAO;
import com.model.User;

@Controller
public class UserValidationController {
	@RequestMapping("/userValidation")
	protected ModelAndView getuserValidationPage() throws SQLException {
		
		ModelAndView model = new ModelAndView("userValidation");
		ArrayList<User> user = new ArrayList<User>();
		Connection conn = DBConnect.openConnection();
		String sql= "SELECT * FROM users WHERE status ='PENDING';";
		try(ResultSet rs = conn.createStatement().executeQuery(sql)){
			while(rs.next()) {
				User uservalidate = new User();
				uservalidate.setUserId(rs.getInt("userId"));
				uservalidate.setFirstName(rs.getString("firstName"));
				uservalidate.setLastName(rs.getString("lastName"));
				uservalidate.setIc(rs.getString("ic"));
				uservalidate.setEmail(rs.getString("email"));
				uservalidate.setPhoneNumber(rs.getString("phoneNumber"));
				uservalidate.setRegion(rs.getString("region"));
				uservalidate.setAddressProof(rs.getBytes("addressProof"));
				uservalidate.setStatus(rs.getString("status"));
				user.add(uservalidate);
			}
		}
		
		model.addObject("user", user);
		return model;
	}
	
	@RequestMapping("/userValidationApprove")
	protected ModelAndView userValidationApprove(@RequestParam("userId") int userId) throws SQLException {
		
		UserValidateDAO userDAO = new UserValidateDAO();
		userDAO.approveUser(userId);
		
		ModelAndView model = new ModelAndView("userValidationResponse");
		model.addObject("message", "Approve successfully");
		return model;
	}
	
	@RequestMapping("/userValidationDisapprove")
	protected ModelAndView userValidationDisapprove(@RequestParam("userId") int userId) throws SQLException {
		
		UserValidateDAO userDAO = new UserValidateDAO();
		userDAO.disapproveUser(userId);
		
		ModelAndView model = new ModelAndView("userValidationResponse");
		model.addObject("message", "Disapprove successfully");
		return model;
	}
	
	@RequestMapping("/userValidationDelete")
	protected ModelAndView userValidationDelete(@RequestParam("userId") int userId) throws SQLException {
		
		UserValidateDAO userDAO = new UserValidateDAO();
		userDAO.deleteUser(userId);
		
		ModelAndView model = new ModelAndView("userValidationResponse");
		model.addObject("message", "Delete successfully");
		return model;
	}
}
