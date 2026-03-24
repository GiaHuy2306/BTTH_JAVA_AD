package com.flashsale.java.entity;

import java.time.LocalDateTime;

public class Orders {
    private int id;
    private int userId;
    private double totalAmount;
    private LocalDateTime orderDate;
    private OrderStatus status;

    public Orders() {
    }

    public Orders(int id, int userId, double totalAmount, LocalDateTime orderDate, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "Id: " + id + "/n" +
                "User Id: " + userId + "/n" +
                "TotalAmount:" + totalAmount + "/n" +
                "OrderDate: " + orderDate + "/n" +
                "Status: " + status +
                '}';
    }
}
