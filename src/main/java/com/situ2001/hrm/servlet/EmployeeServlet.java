package com.situ2001.hrm.servlet;

import com.google.gson.Gson;
import com.situ2001.hrm.dao.EmployeeDao;
import com.situ2001.hrm.dao.impl.EmployeeDaoImpl;
import com.situ2001.hrm.pojo.Employee;
import com.situ2001.hrm.pojo.R;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/employeeList.action"})
public class EmployeeServlet extends HttpServlet {
    private EmployeeDao employeeDao = new EmployeeDaoImpl();

    private String initAndGetAction(HttpServletRequest req, HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        var uri = req.getRequestURI();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var action = initAndGetAction(req, resp);
        if (action.equals("employeeList.action")) {
            employeeList(req, resp);
        }
    }

    private void employeeList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var out = resp.getWriter();


        var page = req.getParameter("page");
        var limit = req.getParameter("limit");

        var name = req.getParameter("name");
        var sex = Integer.parseInt(req.getParameter("sex") == null || req.getParameter("sex").isEmpty() ? "-1" : req.getParameter("sex"));
        var jobId = Integer.parseInt(req.getParameter("jobId") == null || req.getParameter("jobId").isEmpty() ? "-1" : req.getParameter("jobId"));
        var deptId = Integer.parseInt(req.getParameter("deptId") == null || req.getParameter("deptId").isEmpty() ? "-1" : req.getParameter("deptId"));
        var cardId = req.getParameter("cardId");
        var phone = req.getParameter("phone");
        // save to a Employee object
        var employee = new Employee();
        employee.setName(name);
        employee.setSex(sex);
        employee.setJob_id(jobId);
        employee.setDept_id(deptId);
        employee.setCardId(cardId);
        employee.setPhone(phone);


        var list = employeeDao.getEmployeeList(employee);
        R r = new R();
        r.put("msg", "查询成功");
        r.put("data", list);
        r.put("count", 0);
        r.put("code", 0);
        out.print(new Gson().toJson(r));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
