package com.food.utility;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilityClass {

    private static final Log logger = LogFactory.getLog(UtilityClass.class);
    private static BasicDataSource dataSource = null;
    private static final Properties properties = new Properties();

    static {
        initializeDataSource();
    }

    private static void initializeDataSource() {
        try (InputStream input = UtilityClass.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new IOException("application.properties file not found");
            }

            properties.load(input);

            dataSource = new BasicDataSource();
            dataSource.setUrl(properties.getProperty("database.url"));
            dataSource.setUsername(properties.getProperty("database.username"));
            dataSource.setPassword(properties.getProperty("database.password"));
            dataSource.setDriverClassName(properties.getProperty("database.driver-class-name"));

            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxOpenPreparedStatements(100);

            logger.info("MySQL DataSource initialized successfully");

        } catch (Exception e) {
            logger.error("Error initializing datasource", e);
            throw new RuntimeException("Failed to initialize datasource", e);
        }
    }

    public static List<Map<String, String>> executeQuery(String sqlQuery) {
        logger.info("Executing Query: " + sqlQuery);
        List<Map<String, String>> table = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = pstmt.executeQuery()) {

            ResultSetMetaData rsmd = resultSet.getMetaData();
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                columnNames.add(rsmd.getColumnName(i));
            }

            while (resultSet.next()) {
                Map<String, String> row = new HashMap<>();
                for (String columnName : columnNames) {
                    row.put(columnName, resultSet.getString(columnName));
                }
                table.add(row);
            }

        } catch (SQLException e) {
            logger.error("SQL error while executing query: " + sqlQuery, e);
        }

        return table;
    }

    public static int updateSQL(String sqlQuery) throws SQLException {
        int returnIndex;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            returnIndex = pstmt.executeUpdate();

            if (sqlQuery.toLowerCase().startsWith("insert") && returnIndex > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        returnIndex = generatedKeys.getInt(1);
                    }
                }
            }
        }

        return returnIndex;
    }
    
    public static List<Map<String, String>> executeQueryForPreview(String sqlQuery, List<Object> parameters)
            throws SQLException {

        List<Map<String, String>> table = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {

            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    pstmt.setObject(i + 1, parameters.get(i));
                }
            }

            try (ResultSet resultSet = pstmt.executeQuery()) {
                ResultSetMetaData rsmd = resultSet.getMetaData();
                List<String> columnNames = new ArrayList<>();

                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    columnNames.add(rsmd.getColumnName(i));
                }

                while (resultSet.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (String columnName : columnNames) {
                        row.put(columnName, resultSet.getString(columnName));
                    }
                    table.add(row);
                }
            }
        }

        return table;
    }


    public static int updateObject(String sqlQuery, Map<Integer, Object> data) throws SQLException {
        int returnIndex;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            for (Map.Entry<Integer, Object> entry : data.entrySet()) {
                pstmt.setObject(entry.getKey(), entry.getValue());
            }

            returnIndex = pstmt.executeUpdate();

            if (sqlQuery.toLowerCase().startsWith("insert") && returnIndex > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        returnIndex = generatedKeys.getInt(1);
                    }
                }
            }
        }

        return returnIndex;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}