package com.controller;

import com.model.User;
import com.dbUtil.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public ModelAndView registerUser(User user) {
        ModelAndView modelAndView = new ModelAndView("registersuccessful");

        // Perform any additional validation or processing here
        Connection conn = null;
        PreparedStatement registerStmt = null;

        try {
            conn = DBConnect.openConnection();
            conn.setAutoCommit(false); 

            String sql = "INSERT INTO users (Email, IC, FirstName, LastName, Gender, PhoneNumber, "
                    + "Occupation, Address, Category, AddressProof, Region, Password) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            registerStmt = conn.prepareStatement(sql);

            registerStmt.setString(1, user.getEmail());
            registerStmt.setString(2, user.getIc());
            registerStmt.setString(3, user.getFirstName());
            registerStmt.setString(4, user.getLastName());
            registerStmt.setString(5, user.getGender());
            registerStmt.setString(6, user.getPhoneNumber());
            registerStmt.setString(7, user.getOccupation());
            registerStmt.setString(8, user.getAddress());
            registerStmt.setString(9, user.getCategory());
            registerStmt.setString(10, user.getAddressProof());
            registerStmt.setString(11, user.getRegion());
            registerStmt.setString(12, user.getPassword());

            registerStmt.executeUpdate();

            conn.commit(); 

            // Set the user object as a model attribute for the view
            modelAndView.addObject("user", user);
            modelAndView.setViewName("registersuccessful");
        

        } catch (SQLException e) {
            e.printStackTrace();

            modelAndView.setViewName("registration"); 
            modelAndView.addObject("error", "Registration failed. Please try again.");
        } finally {
            try {
                if (registerStmt != null) {
                    registerStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return modelAndView;
    }
}
