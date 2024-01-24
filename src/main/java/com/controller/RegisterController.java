package com.controller;

import com.model.User;
import com.dbUtil.DBConnect;

import java.io.IOException;
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

    @GetMapping("/new")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public ModelAndView registerUser(User user) {
        ModelAndView modelAndView = new ModelAndView("registration-success");

        // Perform any additional validation or processing here
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = DBConnect.openConnection();

            String sql = "INSERT INTO users (Email, IC, FirstName, LastName, Gender, PhoneNumber, "
                    + "Occupation, Address, Category, AddressProof, Region) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getIc());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setString(7, user.getOccupation());
            preparedStatement.setString(8, user.getAddress());
            preparedStatement.setString(9, user.getCategory());
            preparedStatement.setString(10, user.getAddressProof());
            preparedStatement.setString(11, user.getRegion());

            preparedStatement.executeUpdate();

            // Set the user object as a model attribute for the view
            modelAndView.addObject("user", user);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors or validation errors here
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
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
