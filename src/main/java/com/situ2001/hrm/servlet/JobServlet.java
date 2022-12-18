package com.situ2001.hrm.servlet;

import com.google.gson.Gson;
import com.situ2001.hrm.dao.impl.JobDaoImpl;
import com.situ2001.hrm.pojo.Job;
import com.situ2001.hrm.pojo.R;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/jobList.action", "/delJob.action", "/delJobs.action", "/checkJobName.action", "/updJob.action", "/addJob.action"})
public class JobServlet extends HttpServlet {
    private final JobDaoImpl jobDao = new JobDaoImpl();

    private String initAndGetAction(HttpServletRequest req, HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        var uri = req.getRequestURI();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var action = initAndGetAction(req, resp);
        switch (action) {
            case "jobList.action":
                getJobList(req, resp);
                break;
            case "checkJobName.action":
                checkJobName(req, resp);
                break;
        }
    }


    private void checkJobName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var name = req.getParameter("name");
        var result = jobDao.checkName(name);
        if (result) {
            resp.getWriter().print(1);
        } else {
            resp.getWriter().print(0);
        }
    }

    private void getJobList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var page = Integer.parseInt(req.getParameter(("page")));
        var limit = Integer.parseInt(req.getParameter("limit"));

        var name = req.getParameter("name");
        List<Job> list;
        int count = 0;
        if (name == null || name.isEmpty()) {
            list = jobDao.listJob(page, limit);
            count = jobDao.count();
        } else {
            list = jobDao.findJobByName(name, page, limit);
            count = jobDao.countByJobName(name);
        }

        R ret = new R();
        ret.put("msg", "查询成功");
        ret.put("data", list);
        ret.put("count", count);
        ret.put("code", 0);
        resp.getWriter().print(new Gson().toJson(ret));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var action = initAndGetAction(req, resp);
        switch (action) {
            case "delJob.action":
                delJob(req, resp);
                break;
            case "delJobs.action":
                delJobs(req, resp);
                break;
            case "addJob.action":
                addJob(req, resp);
                break;
            case "updJob.action":
                updJob(req, resp);
                break;
        }
    }

    private void addJob(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var name = req.getParameter("name");
        var remark = req.getParameter("remark");
        var job = new Job(name, remark);
        if (jobDao.add(job) > 0) {
            resp.getWriter().print(1);
        } else {
            resp.getWriter().print(0);
        }
    }

    private void delJob(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var id = Integer.parseInt(req.getParameter("id"));
        if (jobDao.delete(id) > 0) {
            resp.getWriter().print(1);
        } else {
            resp.getWriter().print(0);
        }
    }

    private void delJobs(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var idsString = req.getParameter("ids");
        if (jobDao.deleteMany(idsString) > 0) {
            resp.getWriter().print(1);
        } else {
            resp.getWriter().print(0);
        }
    }

    private void updJob(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var id = Integer.parseInt(req.getParameter("id"));
        var name = req.getParameter("name");
        var remark = req.getParameter("remark");
        var job = new Job(id, name, remark);
        var flag = jobDao.update(job);
        if (flag > 0) {
            resp.getWriter().print(1);
        } else {
            resp.getWriter().print(0);
        }
    }
}
