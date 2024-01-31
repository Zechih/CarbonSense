package com.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDashboardDAO {

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

	public float getTotalWaterConsumption() {
		final String WATER_SQL = "SELECT SUM(WaterUsageValueM3) AS totalWaterConsumption FROM waterconsumption WHERE status = 'APPROVED'";

		try (Connection conn = openConnection(); ResultSet waterRs = conn.createStatement().executeQuery(WATER_SQL)) {
			return waterRs.next() ? waterRs.getFloat("totalWaterConsumption") : 0.0f;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0.0f;
		}
	}

	public float getTotalElectricityConsumption() {
		final String ELECTRICITY_SQL = "SELECT SUM(ElectricUsageValueM3) AS totalElectricityConsumption FROM electricityconsumption WHERE status = 'APPROVED'";

		try (Connection conn = openConnection();
				ResultSet electricityRs = conn.createStatement().executeQuery(ELECTRICITY_SQL)) {
			return electricityRs.next() ? electricityRs.getFloat("totalElectricityConsumption") : 0.0f;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0.0f;
		}
	}

	public float getTotalRecycle() {
		final String RECYCLE_SQL = "SELECT SUM(RecycleKG) AS totalRecycle FROM recycle WHERE status = 'APPROVED'";

		try (Connection conn = openConnection();
				ResultSet recycleRs = conn.createStatement().executeQuery(RECYCLE_SQL)) {
			return recycleRs.next() ? recycleRs.getFloat("totalRecycle") : 0.0f;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0.0f;
		}
	}

	public int getTotalParticipants() {
		final String TOTAL_PARTICIPANTS_SQL = "SELECT COUNT(users.userID) AS totalParticipant FROM users WHERE status = 'APPROVED'";

		try (Connection conn = openConnection();
				ResultSet totalPRs = conn.createStatement().executeQuery(TOTAL_PARTICIPANTS_SQL)) {

			return totalPRs.next() ? totalPRs.getInt("totalParticipant") : 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int getTotalSubmissions() {
	    final String TOTAL_SUBMISSIONS_SQL = "SELECT COUNT(application.applicationID) AS totalSubmission FROM application";

	    try (Connection conn = openConnection();
	         ResultSet totalSRs = conn.createStatement().executeQuery(TOTAL_SUBMISSIONS_SQL)) {

	        return totalSRs.next() ? totalSRs.getInt("totalSubmission") : 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}


}
