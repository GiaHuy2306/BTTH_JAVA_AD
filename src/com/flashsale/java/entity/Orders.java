package com.flashsale.java.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateStr = (orderDate != null) ? orderDate.format(formatter) : "N/A";
        return String.format("| %-5d | %-8d | %,15.2f | %-18s | %-10s |", id, userId, totalAmount, dateStr, status);
    }

    public static String getHeader() {
        return String.format("| %-5s | %-8s | %-15s | %-18s | %-10s |\n", "ID", "User ID", "Total Amount", "Order Date", "Status") +
                "-----------------------------------------------------------------------";
    }
}
