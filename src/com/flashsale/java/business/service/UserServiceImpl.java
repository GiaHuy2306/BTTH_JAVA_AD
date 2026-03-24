package com.flashsale.java.business.service;

import com.flashsale.java.entity.Users;
import com.flashsale.java.utils.DatabaseConnectionManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements IUserService {

    @Override
    public List<Users> getTopBuyers() {
        List<Users> topList = new ArrayList<>();
        String sql = "{CALL SP_GetTopBuyers()}";

        try (Connection conn = DatabaseConnectionManager.openConnection();
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
