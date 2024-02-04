package com.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.model.WaterValidation;

public class WaterConDAO {

    public static Connection openConnection() {
        Connection connection = null;

        String dbURL = "jdbc:mysql://localhost:3306/carbonsense";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, username, password);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public WaterValidation getWaterConDetails(int waterID) {
        WaterValidation waterCon = new WaterValidation();
        try (Connection conn = openConnection()) {
            String sql = "SELECT * FROM waterconsumption WHERE waterID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, waterID);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    waterCon.setWaterID(waterID);
                    waterCon.setWaterProportionalFactor(rs.getFloat("waterProportionalFactor"));
                    waterCon.setWaterUsageValueRM(rs.getFloat("waterUsageValueRM"));
                    waterCon.setWaterUsageValueM3(rs.getFloat("waterUsageValueM3"));
                    waterCon.setWaterConsumptionProof(rs.getBytes("waterConsumptionProof"));
                    waterCon.setStatus(rs.getString("status"));
                    return waterCon;
                } else {
                    return waterCon;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return waterCon;
        }
    }

    public void updateWaterCon(float proportionalFactor, float waterUsageRM, float waterUsageM3, byte[] fileBytes,
            int waterID) {
        try (Connection conn = openConnection()) {

            String updateWaterSql = "UPDATE waterconsumption SET waterProportionalFactor = ?, waterUsageValueRM = ?, waterUsageValueM3 = ?, waterConsumptionProof = ?, status = 'PENDING' WHERE waterID = ?";
            try (PreparedStatement waterStmt = conn.prepareStatement(updateWaterSql)) {

                waterStmt.setFloat(1, proportionalFactor);
                waterStmt.setFloat(2, waterUsageRM);
                waterStmt.setFloat(3, waterUsageM3);
                waterStmt.setBytes(4, fileBytes);
                waterStmt.setInt(5, waterID);
                int affectedRows = waterStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateWaterConNoProof(float proportionalFactor, float waterUsageRM, float waterUsageM3, int waterID) {
        try (Connection conn = openConnection()) {

            String updateWaterSql = "UPDATE waterconsumption SET waterProportionalFactor = ?, waterUsageValueRM = ?, waterUsageValueM3 = ?, status = 'PENDING' WHERE waterID = ?";
            try (PreparedStatement waterStmt = conn.prepareStatement(updateWaterSql)) {

                waterStmt.setFloat(1, proportionalFactor);
                waterStmt.setFloat(2, waterUsageRM);
                waterStmt.setFloat(3, waterUsageM3);
                waterStmt.setInt(4, waterID);
                int affectedRows = waterStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertWaterConAndUpdateApplication(float proportionalFactor, float waterUsageRM, float waterUsageM3,
            byte[] fileBytes, int applicationID) throws SQLException {
        String insertWaterSql = "INSERT INTO waterconsumption (waterProportionalFactor, waterUsageValueRM, waterUsageValueM3, waterConsumptionProof, status) VALUES (?, ?, ?, ?, 'PENDING');";

        try (Connection conn = openConnection()) {
            try (PreparedStatement waterStmt = conn.prepareStatement(insertWaterSql,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                waterStmt.setFloat(1, proportionalFactor);
                waterStmt.setFloat(2, waterUsageRM);
                waterStmt.setFloat(3, waterUsageM3);
                waterStmt.setBytes(4, fileBytes);

                int affectedRows = waterStmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = waterStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int waterID = generatedKeys.getInt(1);
                            String updateApplicationSql = "UPDATE application SET waterID = ? WHERE applicationID = ?";
                            try (PreparedStatement updateApplicationStmt = conn
                                    .prepareStatement(updateApplicationSql)) {
                                updateApplicationStmt.setInt(1, waterID);
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

    public void insertWaterConAndCreateApplication(float proportionalFactor, float waterUsageRM, float waterUsageM3,
            byte[] fileBytes, int userID, LocalDate currentDate) throws SQLException {
        String insertWaterSql = "INSERT INTO waterconsumption (waterProportionalFactor, waterUsageValueRM, waterUsageValueM3, waterConsumptionProof, status) VALUES (?, ?, ?, ?, 'PENDING');";

        try (Connection conn = openConnection()) {
            try (PreparedStatement waterStmt = conn.prepareStatement(insertWaterSql,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                waterStmt.setFloat(1, proportionalFactor);
                waterStmt.setFloat(2, waterUsageRM);
                waterStmt.setFloat(3, waterUsageM3);
                waterStmt.setBytes(4, fileBytes);

                int affectedRows = waterStmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = waterStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int waterID = generatedKeys.getInt(1);
                            String insertApplicationSql = "INSERT INTO application (userID, date, waterID) VALUES (?, ?, ?)";
                            try (PreparedStatement insertApplicationStmt = conn
                                    .prepareStatement(insertApplicationSql)) {
                                insertApplicationStmt.setInt(1, userID);
                                insertApplicationStmt.setObject(2, currentDate);
                                insertApplicationStmt.setInt(3, waterID);

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
