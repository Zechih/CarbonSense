package com.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	public static Connection openConnection() {
		Connection connection = null;

		String dbURL = System.getenv("SPRING_DATASOURCE_URL");
		String username = System.getenv("SPRING_DATASOURCE_USERNAME");
		String password = System.getenv("SPRING_DATASOURCE_PASSWORD");

		// Debug logs
		System.out.println("🔍 SPRING_DATASOURCE_URL = " + dbURL);
		System.out.println("🔍 SPRING_DATASOURCE_USERNAME = " + username);
		System.out.println("🔍 SPRING_DATASOURCE_PASSWORD = " + (password != null ? "***" : null));

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(dbURL, username, password);
			System.out.println("✅ Database connection established.");
		} catch (SQLException ex) {
			System.err.println("❌ SQL Exception: " + ex.getMessage());
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			System.err.println("❌ MySQL Driver not found: " + ex.getMessage());
			ex.printStackTrace();
		}

		if (connection == null) {
			System.err.println("⚠️ Connection is null — check DB URL and credentials.");
		}

		return connection;
	}
}
