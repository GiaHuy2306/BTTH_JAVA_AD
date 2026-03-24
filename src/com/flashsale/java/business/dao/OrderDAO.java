package com.flashsale.java.business.dao;

import com.flashsale.java.entity.OrderStatus;
import com.flashsale.java.entity.Orders;
import com.flashsale.java.utils.DatabaseConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements IOrderDao {
    String sql = "INSERT INTO Orders (user_id, total_amount, order_date, status) VALUES (?, ?, ?, ?)";

    @Override
    public int insertOrder(Orders order, Connection conn) throws SQLException {
        try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            pstmt.setInt(1, order.getUserId());
            pstmt.setDouble(2, order.getTotalAmount());
            pstmt.setObject(3, order.getOrderDate());
            pstmt.setString(4, "PENDING");

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                return generatedId;
            }
            return 0;
        }
    }

    @Override
    public List<Orders> getOrdersByUserId(int userId) throws SQLException {
        List<Orders> ordersList = new ArrayList<>();
        String sqlGetOrder = """
                SELECT id, user_id, total_amount, order_date, status FROM Orders where user_id = ?
                """;

        try(Connection conn = DatabaseConnectionManager.openConnection();
        PreparedStatement ps = conn.prepareStatement(sqlGetOrder)){

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int order_id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                double total_amount = rs.getDouble("total_amount");
                LocalDateTime order_date = rs.getObject("order_date", LocalDateTime.class);
                OrderStatus status = OrderStatus.valueOf(rs.getString("status"));

                Orders orders = new Orders(order_id, user_id, total_amount, order_date, status);
                ordersList.add(orders);
            }

        }
        return ordersList;
    }

    @Override
    public void updateOrderStatus(int orderId, String status, Connection conn) {
        String sqlQuery = "SELECT status FROM Orders WHERE id = ?";
        String sqlUpdate = "UPDATE Orders SET status = ? WHERE id = ?";

        try (PreparedStatement psQuery = conn.prepareStatement(sqlQuery)) {
            psQuery.setInt(1, orderId);
            ResultSet rs = psQuery.executeQuery();

            if (rs.next()) {
                String statusCurrent = rs.getString("status");
                if (statusCurrent.equals("PAID")) {
                    System.out.println("Đơn hàng đã thanh toán, không thể đổi trạng thái!");
                    return;
                }

                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                    psUpdate.setString(1, status);
                    psUpdate.setInt(2, orderId);
                    psUpdate.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
