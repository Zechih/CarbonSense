package com.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WaterValidateDAO {

    public static Connection openConnection() {
        Connection connection = null;

        String dbURL = System.getenv("SPRING_DATASOURCE_URL");
        String username = System.getenv("SPRING_DATASOURCE_USERNAME");
        String password = System.getenv("SPRING_DATASOURCE_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, username, password);

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public void approveWater(int waterID) {
        try (Connection conn = openConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("UPDATE waterconsumption SET status = 'APPROVED' WHERE waterID = ?;")) {

            stmt.setInt(1, waterID);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Water consumption approved successfully.");
            } else {
                System.out.println("Failed to approve water consumption.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disapproveWater(int waterID) {
        try (Connection conn = openConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("UPDATE waterconsumption SET status = 'DISAPPROVED' WHERE waterID = ?;")) {

            stmt.setInt(1, waterID);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Water consumption disapproved successfully.");
            } else {
                System.out.println("Failed to disapprove water consumption.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWater(int waterID) {
        try (Connection conn = openConnection()) {

            // Update application table to remove reference to water consumption
            String updateSql = "UPDATE application SET waterID = NULL WHERE waterID = ?;";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, waterID);
                int updateRs = updateStmt.executeUpdate();
                System.out.println(updateRs + " row(s) updated in the application table.");
            }

            // Delete water consumption entry
            String deleteSql = "DELETE FROM waterconsumption WHERE waterID = ?;";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, waterID);
                int deleteRs = deleteStmt.executeUpdate();
                System.out.println(deleteRs + " row(s) deleted from the waterconsumption table.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
