package com.situ2001.hrm.dao.impl;

import com.situ2001.hrm.dao.JobDao;
import com.situ2001.hrm.pojo.Job;
import com.situ2001.hrm.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JobDaoImpl extends JDBCUtils<Job> implements JobDao {
    @Override
    public int add(Job job) {
        return update("insert into job_inf values(null,?,?,0)", job.getName(), job.getRemark());
    }

    @Override
    public int delete(int id) {
        return update("delete from job_inf where id = ?", id);
    }

    @Override
    public int deleteMany(String idSetString) {
        return update("delete from job_inf where id in(" + idSetString + ")");
    }

    @Override
    public int count() {
        return query("select * from job_inf").size();
    }

    @Override
    public int countByJobName(String jobName) {
        return query("select * from job_inf where name like '%" + jobName + "%'").size();
    }

    @Override
    public List<Job> listJob(int page, int limit) {
        return query("select * from job_inf " + "limit " + (page - 1) * limit + "," + limit);
    }

    @Override
    public List<Job> findJobByName(String jobName, int page, int limit) {
        String sqlSb = "select * from job_inf where 1=1" + " and " + "name like '%" + jobName + "%'" +
                " " + "limit" + " " + (page - 1) * limit + "," + limit;
        return query(sqlSb);
    }

    @Override
    public int update(Job job) {
        var id = job.getId();
        var name = job.getName();
        var remark = job.getRemark();
        return update("update job_inf set name=?, remark=? where id=?", name, remark, id);
    }

    @Override
    public boolean checkName(String jobName) {
        var ret = query("select * from job_inf where name=?", jobName);
        return ret.size() == 0;
    }

    @Override
    public Job getBean(ResultSet rs) {
        var job = new Job();
        try {
            job.setId(rs.getInt("ID"));
            job.setState(rs.getInt("STATE"));
            job.setName(rs.getString("NAME"));
            job.setRemark(rs.getString("REMARK"));
            return job;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
