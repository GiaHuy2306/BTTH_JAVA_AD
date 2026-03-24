package com.flashsale.java.business.service;

import com.flashsale.java.entity.OrderDetails;
import com.flashsale.java.entity.Orders;
import com.flashsale.java.entity.Products;

import java.util.List;

public interface IOrderService {
    List<Orders> findAllOrders();
    List<Products> findAllProductDetails();
    void placeOrder(int userId , List<Products> items);
}
