package com.flashsale.java.business.dao;

import com.flashsale.java.entity.Users;
import com.flashsale.java.utils.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public boolean isExist(String username, String email) {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ? OR email = ?";
        try (Connection conn = DatabaseConnectionManager.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean add(Users user) {
        if (isExist(user.getUsername(), user.getEmail())) {
            System.err.println("Username hoặc Email đã tồn tại");
            return false;
        }

        String sql = "INSERT INTO Users (username, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConnectionManager.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Users> getAll() {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection conn = DatabaseConnectionManager.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Users(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Users user) {
        String sql = "UPDATE Users SET username = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseConnectionManager.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        try (Connection conn = DatabaseConnectionManager.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
