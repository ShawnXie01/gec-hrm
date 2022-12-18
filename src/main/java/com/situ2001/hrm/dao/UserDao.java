package com.situ2001.hrm.dao;

import com.situ2001.hrm.pojo.User;

import java.util.List;

public interface UserDao {
    User login(String username, String password);
    // query user
    List<User> userList(String loginname,String status,int page, int limit);
    boolean checkName(String loginname);
    int addUser(User user);
    int updUser(User user);
    int count();
}
