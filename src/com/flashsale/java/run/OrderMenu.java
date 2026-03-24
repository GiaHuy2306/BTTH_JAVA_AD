package com.flashsale.java.run;

import com.flashsale.java.business.service.CardItem;
import com.flashsale.java.business.service.OrderServiceImpl;
import com.flashsale.java.business.service.ProductServiceImpl;
import com.flashsale.java.utils.InputMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderMenu {
    public static void printMenu(Scanner sc, OrderServiceImpl orderService, ProductServiceImpl productService) {
        int choice;
        do {
            System.out.println("==================ORDER FLASH SALE==================");
            System.out.println("1. Đặt hàng");
            System.out.println("0. Thoát");
            System.out.println("========================================================");
            choice = InputMethod.getInputInt("Lựa chọn của bạn : ");
            switch (choice) {
                case 1:
                    handleOrder(orderService,productService);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    break;
            }
        } while (choice != 0);
    }

    public static void handleOrder(OrderServiceImpl orderService,ProductServiceImpl productService) {
        List<CardItem> carts = new ArrayList<>();
        int userId = InputMethod.getInputInt("Nhập id người dùng : ");
        do {
            int productId = InputMethod.getInputInt("Nhập id sản phẩm : ");
            int quantity = InputMethod.getInputInt("Nhập số lượng muốn mua : ");
            carts.add(new CardItem(productId, quantity));
            System.out.println("1. Tiếp tục chọn thêm sản phẩm");
            System.out.println("2. Thanh toán");
            int choice = InputMethod.getInputInt("Lựa chọn của bạn : ");
            if(choice == 2) {
                try{
                    orderService.placeOrder(userId,carts);
                    return;
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    return;
                }
            }
        }while(true);
    }
}
