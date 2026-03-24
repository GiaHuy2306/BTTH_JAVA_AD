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

    public static void getHeader() {
        System.out.printf("| %-5s | %-8s | %-15s | %-18s | %-10s |\n", "ID", "User ID", "Total", "Order Date", "Status");
        System.out.println("-----------------------------------------------------------------------");
    }

    public void displayData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateStr = (this.orderDate != null) ? this.orderDate.format(formatter) : "Null";
        System.out.printf("| %-5d | %-8d | %,15.2f | %-18s | %-10s |\n", this.id, this.userId, this.totalAmount, dateStr, this.status);
    }
}
