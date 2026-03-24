package com.flashsale.java.business.service;

import com.flashsale.java.entity.Products;

public interface IProductService {
    boolean addProduct(Products product);
    boolean deleteProduct(int id);
    boolean updateProduct(int id ,int quantity);
    void displayAll();
    double caculateCategoryRevenue(String category);
}
