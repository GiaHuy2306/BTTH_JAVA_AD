package com.flashsale.java.business.service;

import com.flashsale.java.entity.Users;

import java.util.List;

public interface IUserService {
    boolean addUsers(Users users);
    boolean deleteUsers(int id);
    boolean updateUsers(int id, String username, String email);
    void displayAll();
    void getTopBuyers();

}
