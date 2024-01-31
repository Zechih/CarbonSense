package com.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ElectricValidateDAO {
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
	
	public void approveElectric(int electricityID) {
	    try (Connection conn = openConnection();
	         PreparedStatement stmt = conn.prepareStatement("UPDATE electricityconsumption SET status = 'APPROVED' WHERE electricityID = ?;")) {

	        stmt.setInt(1, electricityID);
	        int affectedRows = stmt.executeUpdate();

	        if (affectedRows > 0) {
	            System.out.println("Electricity consumption approved successfully.");
	        } else {
	            System.out.println("Failed to approve electricity consumption.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void disapproveElectric(int electricityID) {
	    try (Connection conn = openConnection();
	         PreparedStatement stmt = conn.prepareStatement("UPDATE electricityconsumption SET status = 'DISAPPROVED' WHERE electricityID = ?;")) {

	        stmt.setInt(1, electricityID);
	        int affectedRows = stmt.executeUpdate();

	        if (affectedRows > 0) {
	            System.out.println("Electricity consumption disapproved successfully.");
	        } else {
	            System.out.println("Failed to disapprove electricity consumption.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void deleteElectric(int electricityID) {
	    try (Connection conn = openConnection()) {
	    	
	        // Update application table to remove reference to electricity consumption
	        String updateSql = "UPDATE application SET electricityID = NULL WHERE electricityID = ?;";
	        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
	            updateStmt.setInt(1, electricityID);
	            int updateRs = updateStmt.executeUpdate();
	            System.out.println(updateRs + " row(s) updated in the application table.");
	        }

	        // Delete electricity consumption entry
	        String deleteSql = "DELETE FROM electricityconsumption WHERE electricityID = ?;";
	        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
	            deleteStmt.setInt(1, electricityID);
	            int deleteRs = deleteStmt.executeUpdate();
	            System.out.println(deleteRs + " row(s) deleted from the electricityconsumption table.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


}
