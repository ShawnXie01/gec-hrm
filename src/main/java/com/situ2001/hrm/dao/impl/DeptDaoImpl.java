package com.situ2001.hrm.dao.impl;

import com.situ2001.hrm.dao.DeptDao;
import com.situ2001.hrm.pojo.Dept;
import com.situ2001.hrm.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DeptDaoImpl extends JDBCUtils<Dept> implements DeptDao {
    @Override
    public Dept getBean(ResultSet rs) {
        Dept dept = new Dept();
        try {
            dept.setId(rs.getInt("id"));
            dept.setName(rs.getString("name"));
            dept.setRemark(rs.getString("remark"));
            dept.setStatus(rs.getString("state"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dept;
    }
    @Override
    public int addDept(Dept dept) {
        return update("insert into dept_inf values(null, ?, ?, 0)",
                dept.getName(), dept.getRemark());

    }
    @Override
    public int updDept(Dept dept) {
        return update("update dept_inf set name = ?,remark = ? where id = ?",
                dept.getName(), dept.getRemark(), dept.getId());
    }
    @Override
    public int count() {
        List<Dept> depts = query("select * from dept_inf");
        return depts.size();
    }
    @Override
    public int delDept(int id) {
        return update("delete from dept_inf where id = ?", id);
    }


    @Override
    public List<Dept> deptList(String name, String status, int page, int limit) {
        String sql = "select * from dept_inf where 1=1";
        if(name != null && !"".equals(status)) {
            sql += " and name like '%"+ name +"%'";
        }
        if(status != null && !"".equals(status)) {
            sql += " and state = "+ status +"";
        }
        sql += "  limit " + (page - 1) * limit + "," + limit + "";
        List<Dept> depts = query(sql);
        return depts;
    }

    @Override
    public boolean checkDeptName(String name) {
        List<Dept> depts = query("select * from dept_inf where name = ?", name);
        System.out.println("结果长度" + depts.size());
        if(depts.size() > 0 ) {
            System.out.println("首个" + depts.get(0).getName());
            return true;
        }
        return false;
    }

}
