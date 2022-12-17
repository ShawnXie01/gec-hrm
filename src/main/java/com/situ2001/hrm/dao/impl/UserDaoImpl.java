package com.situ2001.hrm.dao.impl;

import com.situ2001.hrm.dao.UserDao;
import com.situ2001.hrm.pojo.User;
import com.situ2001.hrm.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends JDBCUtils<User> implements UserDao {
    @Override
    public User login(String username, String password) {
        List<User> users = query("select * from user_inf where loginname = ? and password = ?", username, password);
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    /**
     * get list of all users
     *
     * @return
     */
    @Override
    public List<User> userList() {
        var users = query("select * from user_inf");
        return users;
    }

    @Override
    public User getBean(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt("ID"));
            user.setLoginname(rs.getString("loginname"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setStatus(rs.getInt("STATUS"));
            user.setCreatedate(rs.getDate("createdate"));
            user.setUsername(rs.getString("username"));
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
