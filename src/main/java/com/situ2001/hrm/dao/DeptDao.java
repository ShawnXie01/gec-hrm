package com.situ2001.hrm.dao;

import com.situ2001.hrm.pojo.Dept;

import java.util.List;

public interface DeptDao {
    public List<Dept> deptList(String name, String status, int page, int limit);
    public boolean checkDeptName(String name);
    public int addDept(Dept dept);
    public int updDept(Dept dept);
    public int count();
    public int delDept(int id);
}
