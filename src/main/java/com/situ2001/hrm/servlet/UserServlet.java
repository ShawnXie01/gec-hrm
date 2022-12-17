package com.situ2001.hrm.servlet;

import com.google.gson.Gson;
import com.situ2001.hrm.dao.impl.UserDaoImpl;
import com.situ2001.hrm.pojo.R;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/userlist.action"})
public class UserServlet extends HttpServlet {
    private UserDaoImpl userDao = new UserDaoImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        resp.setCharacterEncoding("UTF-8");

        String loginname = req.getParameter("username");
        String status = req.getParameter("status");


        var users = userDao.userList(loginname, status);
        R r = new R();
        r.put("msg","查询成功");
        r.put("data",users);
        r.put("count",users.size());
        r.put("code",0);
        resp.getWriter().print(new Gson().toJson(r));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);

    }
}
