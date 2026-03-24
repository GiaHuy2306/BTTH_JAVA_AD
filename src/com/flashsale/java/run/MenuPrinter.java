package com.flashsale.java.run;

import com.flashsale.java.business.service.OrderServiceImpl;
import com.flashsale.java.business.service.ProductServiceImpl;
import com.flashsale.java.business.service.UserServiceImpl;
import com.flashsale.java.utils.DatabaseConnectionManager;
import com.flashsale.java.utils.InputMethod;

import java.util.Scanner;

public class MenuPrinter {
    public static OrderServiceImpl orderService = new OrderServiceImpl();
    public static ProductServiceImpl productService = new ProductServiceImpl();
    public static UserServiceImpl userService = new UserServiceImpl();

    public static void printMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("==================FLASH SALE MANAGEMENT==================");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý khách hàng");
            System.out.println("3. Đặt hàng Flash Sale");
            System.out.println("4. Chạy Stress Test");
            System.out.println("5. Báo cáo & thống kê");
            System.out.println("0. Thoát");
            System.out.println("========================================================");
            choice = InputMethod.getInputInt("Lựa chọn của bạn : ");
            switch (choice) {
                case 1:
                    ProductMenu.printMenu(scanner,productService);
                    break;
                case 2:
                    UserMenu.printMenu(scanner,userService);
                    break;
                case 3:
                    OrderMenu.printMenu(scanner,orderService,productService);
                    break;
                case 4:
                    StressTest.handleStressTest();
                    break;
                case 5:
                    StatisticMenu.printMenu(scanner, userService, productService);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    break;
            }
        } while (choice != 0);
    }

    public static String pickCategory() {
        while (true) {
            System.out.println("Lựa chọn danh mục : ");
            System.out.println("1. Điện tử");
            System.out.println("2. Thời trang");
            System.out.println("3. Mỹ phẩm");
            System.out.println("4. Đồ gia dụng");

            int choice = InputMethod.getInputInt("Lựa chọn của bạn : ");

            String category = switch (choice) {
                case 1 -> "Điện tử";
                case 2 -> "Thời trang";
                case 3 -> "Mỹ phẩm";
                case 4 -> "Đồ gia dụng";
                default -> "";
            };
            if (!category.isEmpty()) {
                return category;
            }

            System.err.println("Lựa chọn không phù hợp! Vui lòng chọn lại.");
        }
    }

}
