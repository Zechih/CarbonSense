package com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbUtil.DBConnect;
import javax.servlet.http.HttpServletRequest;

@Controller()
@RequestMapping("/trainee")

public class TraineeController {
	@RequestMapping("/getAll")
	@ResponseBody()
	public String getAll() {
		String dbURL = "jdbc:mysql://localhost:3306/ip23db";
		String username = "root";
		String password = "";

		String result = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbURL, username, password);
			System.out.println("connection successfully opened :" + connection.getMetaData());

			String sql = "Select * from trainee";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				System.out.println("ID : " + rs.getInt("id"));
				System.out.println("Name : " + rs.getString(2));
				System.out.println("Weight : " + rs.getDouble("weight"));
			}

			stmt.close();
			connection.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return "this is from getAll - Trainee " + result;
	}

	@RequestMapping("/getById")
	@ResponseBody()
	public String getById(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			Connection conn = DBConnect.openConnection();
			String sql = "Select * from trainee where Id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				System.out.println("ID : " + rs.getInt("Id"));
				System.out.println("name : " + rs.getString(2));
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return "this is from getById - Trainee ";

	}

	@RequestMapping("/add")
	@ResponseBody()
	public String add(HttpServletRequest request) {
		int rowAffected = 0;

		try {
			Connection conn = DBConnect.openConnection();
			System.out.println("connection succesfully opened: " + conn.getMetaData());

//			Statement stmt = conn.createStatement();
//			String sql = "INSERT INTO trainee (name, weight, height, bmi) VALUES ('Ahzad', 90.1, 201.19, 87)";
			String sql = "INSERT INTO trainee (name, weight, height, bmi) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, "Adam");
			stmt.setDouble(2, 80);
			stmt.setFloat(3, (float) 180.71);
			stmt.setDouble(4, 60.1);
			rowAffected = stmt.executeUpdate();

			stmt.close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return "this is from add - rowAffected = " + rowAffected;
	}

	@RequestMapping("/delete")
	@ResponseBody()
	public String deleteB(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			Connection conn = DBConnect.openConnection();
			String sql = "Delete from trainee where Id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);
			int rowAffected = stmt.executeUpdate();

			stmt.close();
			conn.close();

			return "Rows affected by delete: " + rowAffected;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return "Error deleting Trainee";
		}

	}

	@RequestMapping("/update")
	@ResponseBody()
	public String update(HttpServletRequest request) {
		int rowAffected = 0;
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			Connection conn = DBConnect.openConnection();
			System.out.println("connection succesfully opened: " + conn.getMetaData());

			String sql = "UPDATE trainee set name = 'updated' WHERE Id = ?" ;
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);
			rowAffected = stmt.executeUpdate();

			stmt.close();
			conn.close();
			
			return "this is from update - rowAffected = " + rowAffected;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return "Error updating Trainee";
		}
	}
}
