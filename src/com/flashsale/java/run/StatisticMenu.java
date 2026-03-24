package com.flashsale.java.run;

import com.flashsale.java.business.service.ProductServiceImpl;
import com.flashsale.java.business.service.UserServiceImpl;
import com.flashsale.java.utils.InputMethod;

import java.util.Scanner;

public class StatisticMenu {
    public static void printMenu(Scanner sc, UserServiceImpl userService, ProductServiceImpl productService) {
        int choice;
        do {
            System.out.println("==================USER MANAGEMENT==================");
            System.out.println("1. Top 5 người mua nhiều hàng nhất");
            System.out.println("2. Thống kê tổng doanh thu theo từng danh mục");
            System.out.println("0. Thoát");
            System.out.println("========================================================");
            choice = InputMethod.getInputInt("Lựa chọn của bạn : ");
            switch (choice){
                case 1:
                    userService.getTopBuyers();
                    break;
                case 2:
                    String category = MenuPrinter.pickCategory();
                    System.out.printf("Tổng doanh thu của danh mục %s  : %.2f\n",category,productService.caculateCategoryRevenue(category));
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không phù hợp!");
                    break;
            }

        } while (choice != 0);
    }
}
