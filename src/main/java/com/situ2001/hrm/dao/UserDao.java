package com.situ2001.hrm.dao;

import com.situ2001.hrm.pojo.User;

import java.util.List;

public interface UserDao {
    public User login(String username, String password);
    // query user
    public List<User> userList(String loginname,String status);
}
