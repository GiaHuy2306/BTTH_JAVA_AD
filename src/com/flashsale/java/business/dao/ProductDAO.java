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
    public boolean updateStock(int productId, int quantityChange) {
        // SQL chống trừ kho âm (stock + change >= 0)
        String sql = "UPDATE Products SET stock = stock + ? " +
                "WHERE id = ? AND (stock + ?) >= 0";

        try (Connection conn = DatabaseConnectionManager.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantityChange);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, quantityChange);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật kho: " + e.getMessage());
            return false;
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


    // 5. Xóa sản phẩm theo ID (Dùng PreparedStatement)
    public boolean delete(int id) {
        // Lưu ý: Nếu sản phẩm đã có trong đơn hàng, câu lệnh này sẽ throw SQLException
        // do ràng buộc Foreign Key trong file DatabaseConnectionManager
        String sql = "DELETE FROM Products WHERE id = ?";

        try (Connection conn = DatabaseConnectionManager.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu xóa thành công

        } catch (SQLException e) {

            System.err.println("Lỗi xóa sản phẩm (Có thể sản phẩm đang nằm trong đơn hàng): " + e.getMessage());
            return false;
        }
    }
}