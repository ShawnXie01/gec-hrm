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
    public List<User> userList(String loginname, String status, int page, int limit) {
        var sql = "select * from user_inf where 1=1";
        if (loginname != null) {
            sql += " and loginname like '%" + loginname + "%'";
        }
        if (status != null && !status.isEmpty()) {
            sql += " and status=" + status;
        }
        // pagination
        sql += " limit " + (page - 1) * limit + "," + limit + "";
        return query(sql);
    }

    @Override
    public int count() {
        return query("select * from user_inf").size();
    }

    @Override
    public int updUser(User user) {
        return update("update user_inf set loginname=?,username=?,status=? where id=?", user.getLoginname(), user.getUsername(), user.getStatus(), user.getId());
    }

    @Override
    public boolean checkName(String loginname) {
        var users = query("select * from user_inf where loginname = ?", loginname);
        return users.size() > 0;
    }

    @Override
    public int addUser(User user) {
        // TODO update utility
        return update("insert into user_inf values(null,?,?,?,CURRENT_TIMESTAMP,?)", user.getLoginname(), user.getPassword(), user.getStatus(), user.getUsername());
    }

    /**
     * delete a user
     *
     * @return
     */
    @Override
    public int delUser(int id) {
        // TODO update utility
        return update("delete from user_inf where id =?", id);
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
