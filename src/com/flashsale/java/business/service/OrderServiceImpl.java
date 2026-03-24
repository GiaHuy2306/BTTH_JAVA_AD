package com.flashsale.java.business.service;

import com.flashsale.java.business.dao.IOrderDao;
import com.flashsale.java.business.dao.IOrderDetailDao;
import com.flashsale.java.business.dao.OrderDAO;
import com.flashsale.java.business.dao.OrderDetailDao;
import com.flashsale.java.business.dao.ProductDAO;
import com.flashsale.java.entity.OrderDetails;
import com.flashsale.java.entity.Orders;
import com.flashsale.java.entity.OrderStatus;
import com.flashsale.java.entity.Products;
import com.flashsale.java.utils.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements IOrderService {
    private final IOrderDao orderDao = new OrderDAO();
    private final IOrderDetailDao orderDetailDao = new OrderDetailDao();
    private final ProductDAO productDao = new ProductDAO();

    @Override
    public List<Orders> findAllOrders() {
        return orderDao.getAllOrder(DatabaseConnectionManager.openConnection());
    }

    @Override
    public List<Products> findAllProductDetails() {
        return productDao.getAll();
    }

    @Override
    public void placeOrder(int userId, List<Products> items) {
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Don hang phai co it nhat 1 san pham.");
        }

        Connection conn = null;
        try {
            conn = DatabaseConnectionManager.openConnection();
            if (conn == null) {
                throw new RuntimeException("Khong mo duoc ket noi database.");
            }

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            double totalAmount = 0;
            List<OrderDetails> orderDetails = new ArrayList<>();
            String updateStockSql = "UPDATE Products SET stock = stock - ? WHERE id = ? AND stock >= ?";

            try (PreparedStatement ps = conn.prepareStatement(updateStockSql)) {
                for (Products item : items) {
                    if (item.getStock() <= 0) {
                        throw new RuntimeException("So luong dat mua phai lon hon 0.");
                    }

                    Products productInDb = productDao.findById(item.getId());
                    if (productInDb == null) {
                        throw new RuntimeException("Khong tim thay san pham co id = " + item.getId());
                    }

                    ps.setInt(1, item.getStock());
                    ps.setInt(2, item.getId());
                    ps.setInt(3, item.getStock());

                    if (ps.executeUpdate() == 0) {
                        throw new RuntimeException("San pham id = " + item.getId() + " da het hang hoac khong du so luong.");
                    }

                    totalAmount += productInDb.getPrice() * item.getStock();
                    orderDetails.add(new OrderDetails(0, 0, item.getId(), item.getStock(), productInDb.getPrice()));
                }
            }

            Orders order = new Orders();
            order.setUserId(userId);
            order.setTotalAmount(totalAmount);
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(OrderStatus.PENDING);

            int orderId = orderDao.insertOrder(order, conn);
            if (orderId <= 0) {
                throw new RuntimeException("Khong tao duoc don hang.");
            }

            orderDetailDao.insertOrderDetails(orderId, orderDetails, conn);
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Rollback that bai.", ex);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }
}
