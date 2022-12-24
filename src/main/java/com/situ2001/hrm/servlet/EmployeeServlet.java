package com.situ2001.hrm.servlet;

import com.google.gson.Gson;
import com.situ2001.hrm.dao.EmployeeDao;
import com.situ2001.hrm.dao.impl.EmployeeDaoImpl;
import com.situ2001.hrm.pojo.Employee;
import com.situ2001.hrm.pojo.R;
import com.situ2001.hrm.util.FormatStringAsDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = {"/employeeList.action", "/checkCardId.action", "/addEmployee.action"})
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
        } else if (action.equals("checkCardId.action")) {
            checkCardId(req, resp);
        }
    }

    private void addEmployee(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        String name = request.getParameter("name");
        String cardId = request.getParameter("cardId");
        String sex = request.getParameter("sex");
        String jobId = request.getParameter("jobId");
        String education = request.getParameter("education");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String tel = request.getParameter("tel");
        String party = request.getParameter("party");
        String qqNum = request.getParameter("gqNum");
        String address = request.getParameter("address");
        String postCode = request.getParameter("postCode");
        String birthday = request.getParameter("birthday");
        String race = request.getParameter("pace");
        String speciality = request.getParameter("speciality");
        String hobby = request.getParameter("hobby");
        String remark = request.getParameter("remark");
        String deptId = request.getParameter("deptId");
        Employee employee = new Employee(name, cardId, address, phone, email, Integer.parseInt(sex), education, new Date(), Integer.parseInt(deptId), Integer.parseInt(jobId), postCode, qqNum, party, FormatStringAsDate.formart(birthday), race, 0, speciality, hobby, remark, tel);

        var out = resp.getWriter();
        int i = employeeDao.addEmployee(employee);
        if (1 > 0) {//添加成功返回1
            System.out.println(i);
//        print, write：当返回的值是页面标签的时候伂用
            out.print(1);
        } else {//添加失敗返回0
            out.print(0);
        }
    }

    private void checkCardId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var id = req.getParameter("cardId");
        var res = employeeDao.selectCardId(id);
        var out = resp.getWriter();
        if (res) {
            out.print(0);
        } else {
            out.print(1);
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
        var action = initAndGetAction(req, resp);
        if (action.equals("addEmployee.action")) {
            addEmployee(req, resp);
        }
    }
}
