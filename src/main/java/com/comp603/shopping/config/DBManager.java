package com.comp603.shopping.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Manages the database connection and ensures the database is initialized
 * automatically on startup (Zero Configuration).
 */
public class DBManager {

    private static final String DB_NAME = "shopping_db";
    // "create=true" ensures Derby creates the DB if it doesn't exist
    private static final String DB_URL = "jdbc:derby:" + DB_NAME + ";create=true";
    private static Connection connection;

    /**
     * Gets a connection to the database. Initializes the DB if necessary.
     *
     * @return The database connection.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
                if (!tablesExist(connection)) {
                    System.out.println("Database not found or empty. Initializing...");
                    initDatabase(connection);
                }
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if the core tables exist in the database.
     */
    private static boolean tablesExist(Connection conn) throws SQLException {
        DatabaseMetaData dbm = conn.getMetaData();
        // Check for 'USERS' table. Derby stores table names in uppercase.
        ResultSet tables = dbm.getTables(null, null, "USERS", null);
        return tables.next();
    }

    /**
     * Reads the schema.sql file and executes the SQL commands to create tables
     * and seed data.
     */
    private static void initDatabase(Connection conn) {
        try (InputStream is = DBManager.class.getResourceAsStream("/schema.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                throw new RuntimeException("schema.sql not found in resources!");
            }

            String sqlScript = reader.lines().collect(Collectors.joining("\n"));
            // Split by semicolon to get individual statements
            String[] statements = sqlScript.split(";");

            try (Statement stmt = conn.createStatement()) {
                for (String sql : statements) {
                    if (!sql.trim().isEmpty()) {
                        stmt.execute(sql.trim());
                    }
                }
            }
            System.out.println("Database initialized successfully with seed data.");

        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Closes the database connection properly.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Shutdown Derby engine (optional but good practice for embedded)
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            // Derby shutdown always throws an exception with SQLState XJ015, which is normal
            if (!"XJ015".equals(e.getSQLState())) {
                e.printStackTrace();
            }
        }
    }
}
