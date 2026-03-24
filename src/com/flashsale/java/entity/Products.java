package com.flashsale.java.entity;

public class Products {
    private int id;
    private String name;
    private double price;
    private String category;
    private int stock;

    public Products() {
    }

    public Products(int id, String name, double price, String category, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public static void getHeader() {
        System.out.printf("| %-5s | %-20s | %-15s | %-15s | %-8s |\n", "ID", "Product Name", "Price", "Category", "Stock");
        System.out.println("-------------------------------------------------------------------------");
    }

    public void displayData() {
        System.out.printf("| %-5d | %-20s | %,15.2f | %-15s | %8d |\n", this.id, this.name, this.price, this.category, this.stock);
    }
}
