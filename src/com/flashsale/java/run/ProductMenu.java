package com.flashsale.java.run;

import com.flashsale.java.business.service.ProductServiceImpl;
import com.flashsale.java.entity.Products;
import com.flashsale.java.utils.InputMethod;

import java.util.Scanner;

public class ProductMenu {
    public static void printMenu(Scanner sc, ProductServiceImpl productService) {
        int choice;
        do {
            int id;
            System.out.println("==================PRODUCT MANAGEMENT==================");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Cập nhật số lượng sản phẩm");
            System.out.println("3. Danh sách sản phẩm");
            System.out.println("0. Thoát");
            System.out.println("========================================================");
            choice = InputMethod.getInputInt("Lựa chọn của bạn : ");
            switch (choice) {
                case 1:
                    Products newProduct = new Products(
                            1,
                            InputMethod.getInputString("Nhập tên sản phẩm : "),
                            InputMethod.getInputDouble("Nhập giá sản phẩm : "),
                            MenuPrinter.pickCategory(),
                            InputMethod.getInputInt("Nhập số lượng sản phẩm : ")
                    );
                    try{
                        if(productService.addProduct(newProduct)){
                            System.out.println("Thêm thành công!");
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    id = InputMethod.getInputInt("Nhập id sản phẩm : ");
                    int updateQuantity = InputMethod.getInputInt("Nhập số lượng muốn cập nhật : ");
                    try{
                        if(productService.updateProduct(id, updateQuantity)){
                            System.out.println("Cập nhật thành công!");
                        }

                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try{
                        productService.displayAll();
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    break;
            }
        } while (choice != 0);
    }
}
