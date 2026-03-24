package com.flashsale.java.entity;

public class OrderDetails {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double price;

    public OrderDetails() {}

    public OrderDetails(int id, int orderId, int productId, int quantity, double price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static void getHeader() {
        System.out.printf("| %-5s | %-8s | %-10s | %-6s | %-15s |\n", "ID", "Order ID", "Prod ID", "Qty", "Price");
        System.out.println("-------------------------------------------------------------------");
    }

    public void displayData() {
        System.out.printf("| %-5d | %-8d | %-10d | %-6d | %,15.2f |\n", this.id, this.orderId, this.productId, this.quantity, this.price);
    }
}
