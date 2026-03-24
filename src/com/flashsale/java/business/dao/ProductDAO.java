package com.flashsale.java.business.dao;

import com.flashsale.java.entity.Products;
import com.flashsale.java.utils.DatabaseConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String PRODUCT_COLUMNS = "id, name, price, category, stock";

    private Products mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Products(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getString("category"),
                rs.getInt("stock")
        );
    }

    // 1. Lấy toàn bộ sản phẩm
    public List<Products> getAll() {
        List<Products> list = new ArrayList<>();
        String sql = "SELECT " + PRODUCT_COLUMNS + " FROM Products";

        try (Connection conn = DatabaseConnectionManager.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAll: " + e.getMessage());
        }
        return list;
    }

    // 2. Tìm theo ID
    public Products findById(int id) {
        String sql = "SELECT " + PRODUCT_COLUMNS + " FROM Products WHERE id = ?";

        try (Connection conn = DatabaseConnectionManager.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findById: " + e.getMessage());
        }
        return null;
    }



    // 3. Cập nhật số lượng kho
    // Chú ý: Nhận Connection từ bên ngoài để chạy Transaction đồng bộ với Order
    public boolean updateStock(Connection conn, int productId, int quantityChange) throws SQLException {
        // SQL này ngăn việc trừ kho quá số lượng hiện có (overselling)
        String sql = "UPDATE Products SET stock = stock + ? WHERE id = ? AND (stock + ?) >= 0";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantityChange); // Ví dụ mua 2 cái thì truyền vào -2
            pstmt.setInt(2, productId);
            pstmt.setInt(3, quantityChange);

            return pstmt.executeUpdate() > 0;
        }
    }

    // 4. Thêm sản phẩm mới
    public boolean insert(Products p) {
        String sql = "INSERT INTO Products (name, price, category, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnectionManager.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getName());
            pstmt.setDouble(2, p.getPrice());
            pstmt.setString(3, p.getCategory());
            pstmt.setInt(4, p.getStock());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi insert: " + e.getMessage());
            return false;
        }
    }
}