package com.flashsale.java.business.dao;

import com.flashsale.java.entity.OrderDetails;
import com.flashsale.java.utils.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDao implements IOrderDetailDao {
    @Override
    public List<OrderDetails> getAllOrderDetail(Connection conn) {
        List<OrderDetails> orderDetails = new ArrayList<>();

        String sqlGetDetail = """
                SELECT id, order_id, product_id, quantity, unit_price FROM Order_Details
                """;

        try (Statement ps = conn.createStatement()) {
            ResultSet rs = ps.executeQuery(sqlGetDetail);
            while (rs.next()) {
                int id = rs.getInt("id");
                int orderId = rs.getInt("order_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                double unitPrice = rs.getDouble("unit_price");

                OrderDetails orderDetail = new OrderDetails(id, orderId, productId, quantity, unitPrice);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderDetails;
    }

    @Override
    public void insertOrderDetails(int orderId, List<OrderDetails> details, Connection conn) throws SQLException {
        String sql = "INSERT INTO Order_Details (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (OrderDetails item : details) {
                pstmt.setInt(1, orderId);
                pstmt.setInt(2, item.getProductId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.setDouble(4, item.getPrice());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        }
    }

    @Override
    public List<OrderDetails> getDetailsByOrderId(int orderId) {
        List<OrderDetails> orderDetails = new ArrayList<>();

        String sqlGetDetail = """
                SELECT id, order_id, product_id, quantity, unit_price FROM Order_Details where order_id = ?
                """;

        try (Connection conn = DatabaseConnectionManager.openConnection();
             PreparedStatement ps = conn.prepareStatement(sqlGetDetail)) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int storedOrderId = rs.getInt("order_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                double unitPrice = rs.getDouble("unit_price");

                OrderDetails orderDetail = new OrderDetails(id, storedOrderId, productId, quantity, unitPrice);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderDetails;
    }
}
