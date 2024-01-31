package com.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dbUtil.DBConnect;

@Controller
public class ProfileController {
    
    @RequestMapping("/dashboard")
    protected void showDashboard(Model model, HttpServletRequest request){
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String ic = request.getParameter("ic");
        String region = request.getParameter("region");


        // Assuming you have a UserDAO class for database interactions
        Connection conn = DBConnect.openConnection();
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
    }

}
