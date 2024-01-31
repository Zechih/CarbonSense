package com.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ElectricConDAO {

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

	public void updateElectricCon(float proportionalFactor, float electricityUsageRM, float electricityUsageM3,
			byte[] fileBytes, int electricityID) {
		try (Connection conn = openConnection()) {

			String updateElectricitySql = "UPDATE electricityconsumption SET electricityProportionalFactor = ?, electricUsageValueRM = ?, electricUsageValueM3 = ?, electricConsumptionProof = ?, status = 'DISAPPROVED' WHERE electricityID = ?";
			try (PreparedStatement electricityStmt = conn.prepareStatement(updateElectricitySql)) {

				electricityStmt.setFloat(1, proportionalFactor);
				electricityStmt.setFloat(2, electricityUsageRM);
				electricityStmt.setFloat(3, electricityUsageM3);
				electricityStmt.setBytes(4, fileBytes);
				electricityStmt.setInt(5, electricityID);
				int affectedRows = electricityStmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void insertElectricConAndUpdateApplication(float proportionalFactor, float electricityUsageRM,
			float electricityUsageM3, byte[] fileBytes, int applicationID) throws SQLException {
		String insertElectricitySql = "INSERT INTO electricityconsumption (electricityProportionalFactor, electricUsageValueRM, electricUsageValueM3, electricConsumptionProof, status) VALUES (?, ?, ?, ?, 'DISAPPROVED');";

		try (Connection conn = openConnection()) {
			try (PreparedStatement electricityStmt = conn.prepareStatement(insertElectricitySql,
					PreparedStatement.RETURN_GENERATED_KEYS)) {
				electricityStmt.setFloat(1, proportionalFactor);
				electricityStmt.setFloat(2, electricityUsageRM);
				electricityStmt.setFloat(3, electricityUsageM3);
				electricityStmt.setBytes(4, fileBytes);

				int affectedRows = electricityStmt.executeUpdate();

				if (affectedRows > 0) {
					try (ResultSet generatedKeys = electricityStmt.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							int electricityID = generatedKeys.getInt(1);
							String updateApplicationSql = "UPDATE application SET electricityID = ? WHERE applicationID = ?";
							try (PreparedStatement updateApplicationStmt = conn
									.prepareStatement(updateApplicationSql)) {
								updateApplicationStmt.setInt(1, electricityID);
								updateApplicationStmt.setInt(2, applicationID);

								int updateApplicationRows = updateApplicationStmt.executeUpdate();

							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public void insertElectricConAndCreateApplication(float proportionalFactor, float electricityUsageRM,
			float electricityUsageM3, byte[] fileBytes, int userID, LocalDate currentDate) throws SQLException {
		String insertElectricitySql = "INSERT INTO electricityconsumption (electricityProportionalFactor, electricUsageValueRM, electricUsageValueM3, electricConsumptionProof, status) VALUES (?, ?, ?, ?, 'DISAPPROVED');";

		try (Connection conn = openConnection()) {
			try (PreparedStatement electricityStmt = conn.prepareStatement(insertElectricitySql,
					PreparedStatement.RETURN_GENERATED_KEYS)) {
				electricityStmt.setFloat(1, proportionalFactor);
				electricityStmt.setFloat(2, electricityUsageRM);
				electricityStmt.setFloat(3, electricityUsageM3);
				electricityStmt.setBytes(4, fileBytes);

				int affectedRows = electricityStmt.executeUpdate();

				if (affectedRows > 0) {
					try (ResultSet generatedKeys = electricityStmt.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							int electricityID = generatedKeys.getInt(1);
							String insertApplicationSql = "INSERT INTO application (userID, date, electricityID) VALUES (?, ?, ?)";
							try (PreparedStatement insertApplicationStmt = conn
									.prepareStatement(insertApplicationSql)) {
								insertApplicationStmt.setInt(1, userID);
								insertApplicationStmt.setObject(2, currentDate);
								insertApplicationStmt.setInt(3, electricityID);

								int insertApplicationRows = insertApplicationStmt.executeUpdate();
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}