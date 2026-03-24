package com.flashsale.java.business.dao;

import com.flashsale.java.entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IOrderDetailDao {
    void insertOrderDetails(int orderid, List<OrderDetails> details, Connection conn) throws SQLException;

    List<OrderDetails> getDetailsByOrderId(int orderId);
}
