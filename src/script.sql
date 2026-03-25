CREATE DATABASE IF NOT EXISTS flash_sale;
USE flash_sale;
-- bảng Users
CREATE TABLE IF NOT EXISTS Users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
    );

-- bảng Products
CREATE TABLE IF NOT EXISTS Products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(15,2) NOT NULL CHECK (price > 0),
    category VARCHAR(255) NOT NULL,
    stock INT NOT NULL CHECK (stock >= 0)
    );

-- bảng Orders
CREATE TABLE IF NOT EXISTS Orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL CHECK (total_amount >= 0),
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDING', 'PAID', 'CANCEL') DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES Users(id)
    );

-- bảng Order_Details
CREATE TABLE IF NOT EXISTS Order_Details (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT CHECK (quantity >= 0),
    unit_price DECIMAL(15,2) NOT NULL CHECK (unit_price >= 0),
    FOREIGN KEY (product_id) REFERENCES Products(id),
    FOREIGN KEY (order_id) REFERENCES Orders(id)
    );

DROP FUNCTION IF EXISTS FUNC_CalculateCategoryRevenue;
CREATE FUNCTION FUNC_CalculateCategoryRevenue(p_category VARCHAR(255))
    RETURNS DECIMAL(15,2)
    DETERMINISTIC
BEGIN
    DECLARE total DECIMAL(15,2);

    SELECT SUM(od.quantity * od.unit_price)
    INTO total
    FROM Order_Details od
             JOIN Products p ON od.product_id = p.id
    WHERE p.category = p_category;

    RETURN IFNULL(total, 0);
END;


DROP PROCEDURE IF EXISTS SP_GetTopBuyers;
CREATE PROCEDURE SP_GetTopBuyers()
BEGIN
    SELECT
        u.id,
        u.username,
        u.email,
        SUM(o.total_amount) AS total_spent,
        COUNT(o.id) AS total_orders
    FROM users u
             JOIN orders o ON u.id = o.user_id
    GROUP BY u.id, u.username, u.email
    ORDER BY total_spent DESC
        LIMIT 5;
END;