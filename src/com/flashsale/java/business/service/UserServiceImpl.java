package com.flashsale.java.business.service;

import com.flashsale.java.business.dao.UserDAO;
import com.flashsale.java.entity.Products;
import com.flashsale.java.entity.Users;
import com.flashsale.java.utils.DatabaseConnectionManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements IUserService {
    private final UserDAO userDAO = new UserDAO();
    @Override
    public boolean addUsers(Users users) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (users == null) {
            throw new IllegalArgumentException("User không được null");
        }

        if(!users.getEmail().matches(regex)){
            throw new IllegalArgumentException("Email không hợp lệ");
        }

        return userDAO.add(users);
    }

    @Override
    public boolean deleteUsers(int id) {
        if(userDAO.getById(id) == null){
            throw new RuntimeException("Không tìm thấy người dùng!");
        }
        return  userDAO.delete(id);
    }

    @Override
    public boolean updateUsers(int id, String username, String email) {
        if(userDAO.getById(id) == null){
            throw new RuntimeException("Không tìm thấy người dùng!");
        }
        return userDAO.update(id, username, email);
    }

    @Override
    public void displayAll() {
        List<Users> users = userDAO.getAll();
        if(users.isEmpty()){
            throw new RuntimeException("Danh sách người dùng trống!");
        }

        System.out.println("======= Danh sách người dùng =======");
        Users.getHeader();
        users.stream().forEach(u -> u.displayData());
    }

    @Override
    public void getTopBuyers() {
        String sql = "{CALL SP_GetTopBuyers()}";

        System.out.println("\n======= TOP 5 KHÁCH HÀNG CHI TIÊU NHIỀU NHẤT =======");
        System.out.printf("| %-5s | %-20s | %-15s | %-10s |\n", "ID", "Tên khách hàng", "Tổng chi (VNĐ)", "Số đơn");
        System.out.println("------------------------------------------------------------");

        try (Connection conn = DatabaseConnectionManager.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));

                double spent = rs.getDouble("total_spent");
                int orders = rs.getInt("total_orders");

                System.out.printf("| %-5d | %-20s | %,15.2f | %-10d |\n",
                        user.getId(), user.getUsername(), spent, orders);
            }
            System.out.println("------------------------------------------------------------");

        } catch (SQLException e) {
            System.err.println("Lỗi báo cáo: " + e.getMessage());
        }
    }
}
