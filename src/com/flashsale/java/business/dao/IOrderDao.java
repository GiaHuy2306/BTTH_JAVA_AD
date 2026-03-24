package com.flashsale.java.business.dao;

import com.flashsale.java.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IOrderDao {

    List<Orders> getAllOrder(Connection conn);

    int insertOrder(Orders order, Connection conn) throws SQLException;

    List<Orders> getOrdersByUserId(int userId) throws SQLException;

    void updateOrderStatus(int orderId, String status, Connection conn);
}
