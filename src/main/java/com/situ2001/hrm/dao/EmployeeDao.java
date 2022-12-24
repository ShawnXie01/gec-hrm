package com.situ2001.hrm.dao;

import com.situ2001.hrm.pojo.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getEmployeeList(Employee employee);
    boolean selectCardId(String id);
    int addEmployee(Employee employee);
    int updEmployee(Employee employee);
}
