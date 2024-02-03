package com.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserValidateDAO {
	public static Connection openConnection() {
		Connection connection = null;

		String dbURL = "jdbc:mysql://localhost:3306/carbonsense";
		String username = "root";
		String password = "";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(dbURL, username, password);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return connection;
	}
	
	public void approveUser(int userID) {
	    try (Connection conn = openConnection();
	         PreparedStatement stmt = conn.prepareStatement("UPDATE users SET status = 'APPROVED' WHERE userID = ?;")) {

	        stmt.setInt(1, userID);
	        int affectedRows = stmt.executeUpdate();

	        if (affectedRows > 0) {
	            System.out.println("User account approved successfully.");
	        } else {
	            System.out.println("Failed to approve user account.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void disapproveUser(int userID) {
	    try (Connection conn = openConnection();
	         PreparedStatement stmt = conn.prepareStatement("UPDATE users SET status = 'DISAPPROVED' WHERE userID = ?;")) {

	        stmt.setInt(1, userID);
	        int affectedRows = stmt.executeUpdate();

	        if (affectedRows > 0) {
	            System.out.println("User account disapproved successfully.");
	        } else {
	            System.out.println("Failed to disapprove user account.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void deleteUser(int userID) {
	    try (Connection conn = openConnection()) {

	        // Delete electricity consumption entry
	        String deleteSql = "DELETE FROM users WHERE userID = ?;";
	        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
	            deleteStmt.setInt(1, userID);
	            int deleteRs = deleteStmt.executeUpdate();
	            System.out.println(deleteRs + " row(s) deleted from the user table.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


}
