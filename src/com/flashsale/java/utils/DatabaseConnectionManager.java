package com.flashsale.java.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseConnectionManager {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/flash_sale?createDatabaseIfNotExist=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123";

    //Singleton
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
            return null;
        }
    }

    // hàm tự động đọc file
    public static void initDB(String filePath) {
        try {
            String content = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
            content = content.replaceAll("(?m)^--.*", "");

            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                String[] queries = content.split(";");

                StringBuilder currentQuery = new StringBuilder();
                for (String query : queries) {
                    currentQuery.append(query);
                    String sql = currentQuery.toString().trim();

                    if (sql.isEmpty()) continue;
                    if (sql.toUpperCase().contains("BEGIN") && !sql.toUpperCase().contains("END")) {
                        currentQuery.append(";");
                        continue;
                    }

                    stmt.execute(sql);
                    currentQuery.setLength(0);
                }
                System.out.println("Thực thi script " + filePath + " thành công!");
            }
        } catch (Exception e) {
            System.err.println("Lỗi thực thi SQL: " + e.getMessage());
        }
    }

    static void main(String[] args) {
        Connection conn = getConnection();
        initDB("src/script.sql");
    }
}




