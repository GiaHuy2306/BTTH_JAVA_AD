package com.flashsale.java.run;

import com.flashsale.java.business.service.UserServiceImpl;
import com.flashsale.java.entity.Users;
import com.flashsale.java.utils.InputMethod;

import java.util.Scanner;

public class UserMenu {
    public static void printMenu(Scanner scanner, UserServiceImpl userService) {
        int choice;
        do {
            int id;
            System.out.println("==================USER MANAGEMENT==================");
            System.out.println("1. Thêm người dùng");
            System.out.println("2. Cập nhật thông tin người dùng");
            System.out.println("3. Danh sách người dùng");
            System.out.println("0. Thoát");
            System.out.println("========================================================");
            choice = InputMethod.getInputInt("Lựa chọn của bạn : ");
            switch (choice) {
                case 1:
                    Users newUser = new Users(
                            1,
                            InputMethod.getInputString("Nhập email : "),
                            InputMethod.getInputString("Nhập username : ")
                            );
                    try{
                        if(userService.addUsers(newUser)){
                            System.out.println("Thêm thành công!");
                        }
                    }catch(Exception e){
                        System.out.println("Lỗi : "+e.getMessage());
                    }
                    break;
                case 2:
                    id = InputMethod.getInputInt("Nhập id người dùng : ");
                    String usernameUpdate = InputMethod.getInputString("Nhập username mới : ");
                    String emailUpdate = InputMethod.getInputString("Nhập email mới : ");
                    try{
                        if(userService.updateUsers(id,usernameUpdate,emailUpdate)){
                            System.out.println("Cập nhật thành công!");
                        }

                    }catch(Exception e){
                        System.out.println("Lỗi : "+e.getMessage());
                    }
                    break;
                case 3:
                    try{
                        userService.displayAll();
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
