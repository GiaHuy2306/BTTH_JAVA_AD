package com.flashsale.java.business.service;

import com.flashsale.java.business.dao.ProductDAO;
import com.flashsale.java.entity.Products;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;

public class ProductServiceImpl implements IProductService {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    public boolean addProduct(Products product) {

        if (product == null) {
            throw new IllegalArgumentException("Sản phẩm không được null");
        }

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không hợp lệ");
        }

        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Giá phải lớn hơn 0");
        }

        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Số lượng không được âm");
        }

        return productDAO.insert(product);
    }

    @Override
    public boolean deleteProduct(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID không tồn tại");
        }

        return productDAO.delete(id);
    }

    @Override
    public boolean updateProduct(int id, int quantity) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID không tồn tại");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Số lượng không được âm");
        }
        return productDAO.updateStock(id, quantity);
    }

    @Override
    public void displayAll() {
        List<Products> products = productDAO.getAll();

        System.out.println("======= Danh sách sản phẩm =======");
        products.stream().forEach(element -> element.displayData());
    }

    @Override
    public double caculateCategoryRevenue(String category) {
        double result = 0;
        try (Connection conn = utils.DatabaseConnectionManager.getConnection();
             CallableStatement cs = conn.prepareCall("{? = call FUNC_CalculateCategoryRevenue(?)}")) {

            cs.registerOutParameter(1, Types.DOUBLE);
            cs.setString(2, category);

            cs.execute();

            result = cs.getDouble(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
