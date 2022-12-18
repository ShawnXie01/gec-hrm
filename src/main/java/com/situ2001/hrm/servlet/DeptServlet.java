package com.situ2001.hrm.servlet;

import com.google.gson.Gson;
import com.situ2001.hrm.dao.DeptDao;
import com.situ2001.hrm.dao.impl.DeptDaoImpl;
import com.situ2001.hrm.pojo.Dept;
import com.situ2001.hrm.pojo.R;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/deptList.action", "/checkDeptName.action", "/addDept.action", "/updDept.action", "/delDept.action", "/delDepts.action"})
public class DeptServlet extends HttpServlet {
    private DeptDao deptDao = new DeptDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String uri = request.getRequestURI();
//        out.println("请求路径：" + uri);
        // 截取uri
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        if (action.equals("deptList.action")) {
            deptlist(request, response);
        } else if (action.equals("addDept.action")) {
            addDept(request, response);
        } else if (action.equals("checkDeptName.action")) {
            checkDeptName(request, response);
        } else if (action.equals("updDept.action")) {
            updDept(request, response);
        } else if (action.equals("delDept.action")) {
            delDept(request, response);
        } else if (action.equals("delDepts.action")) {
            delDepts(request, response);
        }

    }

    private void delDepts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String ids = request.getParameter("ids");
        String[] id = ids.split(",");
        int state = 0;
        for (int i = 0; i < id.length; i++) {
            state = deptDao.delDept(Integer.parseInt(id[i]));
        }
        if (state > 0) {
            // 刪除成功
            out.print(0);
        } else {
            out.print(-1);
        }
    }

    private void delDept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
        int i = deptDao.delDept(id);
        if (i > 0) {
            // 刪除成功
            out.print(0);
        } else {
            out.print(-1);
        }
    }

    private void updDept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("name: " + name + " -- remark: " + remark);
        Dept dept = new Dept(name, remark, id);
        int i = deptDao.updDept(dept);
        if (i > 0) {
            // 修改成功
            out.print(1);
        } else {
            out.print(0);
        }

    }

    private void checkDeptName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        PrintWriter out = response.getWriter();
        boolean flag = deptDao.checkDeptName(name);
        if (flag) {
            // 部门名已存在
            out.print(0);
        } else {
            // 不存在
            out.print(1);
        }

    }

    private void addDept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        Dept dept = new Dept(name, remark);
        int i = deptDao.addDept(dept);
        if (i > 0) {
            // 添加成功
            out.print(1);
        } else {
            out.print(0);
        }

    }

    private void deptlist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8"); // TODO
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String status = request.getParameter("status");
        int limit = Integer.parseInt(request.getParameter("limit"));
        int page = Integer.parseInt(request.getParameter("page"));
        List<Dept> depts = deptDao.deptList(name, status, page, limit);
        // 类似LoginServlet，使用layui框架
        R r = new R();
        r.put("msg", "查询成功");
        r.put("data", depts);
        r.put("count", deptDao.count());
        r.put("code", 0);
        // 将对象返回到页面
        out.print(new Gson().toJson(r));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
