package com.flashsale.java.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionManager {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/flash_sale?createDatabaseIfNotExist=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123";

    public static Connection openConnection(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi : Chưa cài đặt MySQL Driver");
        } catch (SQLException e) {
            System.err.println("Lỗi SQL : Kết nối thất bại");
        }
        return null;
    }

    public static void initDB(){
        try(
                Connection conn = openConnection();
                Statement stmt = conn.createStatement();
        ){
            String users = "create table if not exists Users(" +
                    "id int primary key auto_increment, " +
                    "name varchar(255) not null, " +
                    "email varchar(255) not null unique" +
                    ");";

            String products = "create table if not exists Products(" +
                    "id int primary key auto_increment, " +
                    "name varchar(255) not null, " +
                    "price DECIMAL(15,2) not null check ( price > 0 ), " +
                    "category varchar(255) not null, " +
                    "stock int not null check ( stock >= 0 )" +
                    ");";

            String orders = "create table if not exists Orders(" +
                    "id int primary key auto_increment, " +
                    "user_id int not null, " +
                    "foreign key (user_id) references Users(id), " +
                    "total_amount decimal(15,2) not null check ( total_amount >= 0 ), " +
                    "order_date DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "status ENUM('PENDING', 'PAID', 'CANCEL') DEFAULT 'PENDING'" +
                    ");";

            String order_detail = "create table if not exists Order_Details(" +
                    "id int primary key auto_increment, " +
                    "order_id int not null, " +
                    "product_id int not null, " +
                    "foreign key (product_id) references Products(id), " +
                    "foreign key (order_id) references Orders(id), " +
                    "quantity int check (quantity >= 0 ), " +
                    "unit_price DECIMAL(15,2) not null check ( unit_price >= 0 ) " +
                    ");";

            String getTopBuyers = """
                    DELIMITER $$
                    
                    DROP PROCEDURE IF EXISTS SP_GetTopBuyers $$
                    
                    CREATE PROCEDURE SP_GetTopBuyers()
                    BEGIN
                        SELECT\s
                            u.id,
                            u.name,
                            u.email,
                            SUM(o.total_amount) AS total_spent
                        FROM users u
                        JOIN orders o ON u.id = o.user_id
                        GROUP BY u.id, u.name, u.email
                        ORDER BY total_spent DESC
                        LIMIT 5;
                    END $$
                    
                    DELIMITER ;
                    """;
            stmt.executeUpdate(users);
            stmt.executeUpdate(products);
            stmt.executeUpdate(orders);
            stmt.executeUpdate(order_detail);
            stmt.executeUpdate(getTopBuyers);
        }catch(Exception e){
            System.out.println("Error : " +e.getMessage());
        }
    }

    static void main(String[] args) {
        Connection con = openConnection();
        DatabaseConnectionManager.initDB();
    }
}
