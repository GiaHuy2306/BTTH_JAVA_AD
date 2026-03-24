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
        if (users == null) {
            throw new IllegalArgumentException("User không được null");
        }

        if (users.getUsername() == null || users.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên user không hợp lệ");
        }

        if (users.getEmail() == null || users.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }

        return userDAO.add(users);
    }

    @Override
    public boolean deleteUsers(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID không tồn tại");
        }
        return  userDAO.delete(id);
    }

    @Override
    public boolean updateUsers(Users users) {
        if (users.getId() <= 0) {
            throw new IllegalArgumentException("ID không tồn tại");
        }

        return userDAO.update(users);
    }

    @Override
    public void displayAll() {
        List<Users> users = userDAO.getAll();
        System.out.println("======= Danh sách người dùng =======");
        users.stream().forEach(u -> u.displayData());
    }

    @Override
    public List<Users> getTopBuyers() {
        List<Users> topList = new ArrayList<>();
        String sql = "{CALL SP_GetTopBuyers()}";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             CallableStatement stmt = conn.prepareCall(sql))
        {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                topList.add(user);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return topList;
    }
}
