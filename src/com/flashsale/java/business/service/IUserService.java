package com.flashsale.java.business.service;

import com.flashsale.java.entity.Users;

import java.util.List;

public interface IUserService {
    boolean addUsers(Users users);
    boolean deleteUsers(int id);
    boolean updateUsers(Users users);
    void displayAll();
    List<Users> getTopBuyers();

}
