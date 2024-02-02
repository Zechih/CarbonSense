package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dbUtil.DBConnect;
import com.model.User;

@Controller
public class ProfileController {

    @RequestMapping("/home")
    protected ModelAndView getHomePage(HttpServletRequest request, HttpSession session) {
        ModelAndView model = new ModelAndView("dashboard");

        // Retrieve email from the session
        String email = (String) session.getAttribute("email");

        try (Connection conn = DBConnect.openConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email=?")) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setIc(rs.getString("ic"));
                    user.setRegion(rs.getString("region"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setAddress(rs.getString("address"));
                    user.setCategory(rs.getString("category"));

                    model.addObject("user", user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately (logging, returning an error view, etc.)
        }

        return model;
    }

    @RequestMapping("/profile")
    protected ModelAndView showProfile(HttpServletRequest request, HttpSession session) {

        ModelAndView model = new ModelAndView("profile");

        // Retrieve email from the session
        String email = (String) session.getAttribute("email");

        try (Connection conn = DBConnect.openConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email=?")) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setIc(rs.getString("ic"));
                    user.setRegion(rs.getString("region"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setAddress(rs.getString("address"));
                    user.setCategory(rs.getString("category"));
                    user.setOccupation(rs.getString("occupation"));
                    user.setEmail(rs.getString("email"));

                    model.addObject("user", user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately (logging, returning an error view, etc.)
        }

        return model;
    }

    @RequestMapping("/update")
    protected ModelAndView updateProfile(HttpServletRequest request, HttpSession session) {

        ModelAndView model = new ModelAndView("editprofile");

        // Retrieve email from the session
        String email = (String) session.getAttribute("email");

        try (Connection conn = DBConnect.openConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email=?")) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setIc(rs.getString("ic"));
                    user.setRegion(rs.getString("region"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setAddress(rs.getString("address"));
                    user.setCategory(rs.getString("category"));
                    user.setOccupation(rs.getString("occupation"));
                    user.setEmail(rs.getString("email"));

                    model.addObject("user", user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately (logging, returning an error view, etc.)
        }

        return model;
    }

    @PostMapping("/updateProfileProcess")
    protected ModelAndView updateProfileProcess(HttpServletRequest request, HttpSession session,
            @ModelAttribute("user") User updatedUser) {

        ModelAndView model;

        try (Connection conn = DBConnect.openConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE users SET firstName=?, lastName=?, ic=?, region=?, phoneNumber=?, address=?, category=?, occupation=? WHERE email=?")) {

            stmt.setString(1, updatedUser.getFirstName());
            stmt.setString(2, updatedUser.getLastName());
            stmt.setString(3, updatedUser.getIc());
            stmt.setString(4, updatedUser.getRegion());
            stmt.setString(5, updatedUser.getPhoneNumber());
            stmt.setString(6, updatedUser.getAddress());
            stmt.setString(7, updatedUser.getCategory());
            stmt.setString(8, updatedUser.getOccupation());
            stmt.setString(9, updatedUser.getEmail());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Update successful, you can redirect to the profile page or any other page
                model = new ModelAndView("redirect:/profile");
            } else {
                // Update failed, handle accordingly (e.g., show an error message)
                model = new ModelAndView("editprofile");
                model.addObject("errorMessage", "Failed to update profile. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately (logging, returning an error view, etc.)
            model = new ModelAndView("editprofile");
            model.addObject("errorMessage", "An error occurred while updating profile. Please try again.");
        }

        return model;
    }

}
