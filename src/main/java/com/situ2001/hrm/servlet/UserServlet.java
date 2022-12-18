package com.situ2001.hrm.servlet;

import com.google.gson.Gson;
import com.situ2001.hrm.dao.impl.UserDaoImpl;
import com.situ2001.hrm.pojo.R;
import com.situ2001.hrm.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/userlist.action", "/checkName.action", "/addUser.action", "/updUser.action"})
public class UserServlet extends HttpServlet {
    private final UserDaoImpl userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1);

        if (action.equals("userlist.action")) {
            userList(req, resp);
        } else if (action.equals("checkName.action")) {
            checkName(req, resp);
        } else if (action.equals("addUser.action")) {
            addUser(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1);

        if (action.equals("addUser.action")) {
            addUser(req, resp);
        } else if (action.equals("updUser.action")) {
            updUser(req, resp);
        }
    }

    /**
     * edit an user
     *
     * @param req
     * @param resp
     */
    private void updUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var out = resp.getWriter();
        var loginname = req.getParameter("loginname");
        var username = req.getParameter("username");
        var status = Integer.parseInt(req.getParameter("status"));
        var id = Integer.parseInt(req.getParameter("id"));
        var user = new User(id, loginname, status, username);
        var ret = userDao.updUser(user);
        if (ret > 0) {
            out.print(1);
        } else {
            out.print(0);
        }
    }

    private void userList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String loginname = req.getParameter("username");
        String status = req.getParameter("status");


        var users = userDao.userList(loginname, status);
        R r = new R();
        r.put("msg", "查询成功");
        r.put("data", users);
        r.put("count", 0); // TODO 分页
        r.put("code", 0);
        resp.getWriter().print(new Gson().toJson(r));
    }

    private void checkName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var out = resp.getWriter();
        String loginname = req.getParameter("loginname");
        var flag = userDao.checkName(loginname);
        if (flag) {
            out.print(0);
        } else {
            out.print(1);
        }
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var out = resp.getWriter();
        var loginname = req.getParameter("loginname");
        var password = req.getParameter("password");
        var username = req.getParameter("username");
        var status = Integer.parseInt(req.getParameter("status"));
        var user = new User(loginname, password, status, username);
        var ret = userDao.addUser(user);
        if (ret > 0) {
            out.print(1);
        } else {
            out.print(0);
        }
    }
}
