package com.situ2001.hrm.dao.impl;

import com.situ2001.hrm.dao.EmployeeDao;
import com.situ2001.hrm.pojo.Employee;
import com.situ2001.hrm.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDaoImpl extends JDBCUtils<Employee> implements EmployeeDao {
    @Override
    public int count() {
        return query("select * from employee_inf").size();
    }

    @Override
    public List<Employee> getEmployeeList(Employee employee, int page, int limit) {
        var sql = "select e.*,d.`NAME` deptName,j.`NAME` jobName from employee_inf e,dept_inf d,job_inf j where d.ID=e.dept_id and e.job_id=j.ID";
        if (employee.getName() != null && !employee.getName().isEmpty()) {
            sql += " AND e.name like '%" + employee.getName() + "%'";
        }
        if (employee.getSex() != -1) {
            sql += " AND sex=" + employee.getSex();
        }
        if (employee.getCardId() != null && !employee.getCardId().isEmpty()) {
            sql += " AND card_id=" + employee.getCardId();
        }
        if (employee.getJobId() != -1) {
            sql += " AND job_id=" + employee.getJobId();
        }
        if (employee.getDeptId() != -1) {
            sql += " AND dept_id=" + employee.getDeptId();
        }
        if (employee.getPhone() != null && !employee.getPhone().isEmpty()) {
            sql += " AND phone=" + employee.getPhone();
        }
        sql += " limit " + (page - 1) * limit + "," + limit + "";
        var list = query(sql);
        return list;
    }

    @Override
    public boolean selectCardId(String id) {
        var sql = "select e.*,d.`NAME` deptName,j.`NAME` jobName from employee_inf e,dept_inf d,job_inf j where d.ID=e.dept_id and e.job_id=j.ID" + " and card_id=?";
        var list = query(sql, id);
        return list.size() > 0;
    }

    @Override
    public int addEmployee(Employee employee) {
        return update("insert into employee_inf values (nUll, ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ? , ?)",
                employee.getName(), employee.getCardId(), employee.getAddress(), employee.getPostCode(), employee.getTel(), employee.getPhone(), employee.getQqNum(), employee.getEmail(), employee.getSex(), employee.getParty(), employee.getBirthday(), employee.getRace(), employee.getEducation(), employee.getSpeciality(), employee.getHobby(), employee.getRemark(), employee.getCreateDate(), employee.getState(), employee.getDeptId(), employee.getJobId());
    }

    @Override
    public int delete(int id) {
        return update("delete from employee_inf where id = ?", id);
    }

    @Override
    public int deleteMany(String idSetString) {
        return update("delete from employee_inf where id in" + idSetString);
    }

    @Override
    public int updEmployee(Employee employee) {
        return update("update employee_inf set name=?,card_id=?,address=?,post_code=?,tel=?,phone=?,qq_num=?,email=?,sex=?,party=?,birthday=?,race=?,education=?,speciality=?,hobby=?,remark=?,dept_id=? where id=?",
                employee.getName(), employee.getCardId(), employee.getAddress(), employee.getPostCode(), employee.getTel(), employee.getPhone(), employee.getQqNum(), employee.getEmail(), employee.getSex(), employee.getParty(), employee.getBirthday(), employee.getRace(), employee.getEducation(), employee.getSpeciality(), employee.getHobby(), employee.getRemark(), employee.getDeptId(), employee.getId());
    }

    @Override
    public Employee getBean(ResultSet rs) {
        Employee employee = new Employee();
        try {
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setCardId(rs.getString("card_id"));
            employee.setAddress(rs.getString("address"));
            employee.setPostCode(rs.getString("POST_CODE"));
            employee.setTel(rs.getString("tel"));
            employee.setPhone(rs.getString("phone"));
            employee.setQqNum(rs.getString("qq_num"));
            employee.setEmail(rs.getString("email"));
            employee.setSex(rs.getInt("sex"));
            employee.setParty(rs.getString("party"));
            employee.setBirthday(rs.getDate("birthday"));
            employee.setRace(rs.getString("race"));
            employee.setEducation(rs.getString("education"));
            employee.setSpeciality(rs.getString("SPECIALITY"));
            employee.setHobby(rs.getString("HOBBY"));
            employee.setRemark(rs.getString("REMARK"));
            employee.setCreateDate(rs.getDate("CREATE_DATE"));
            employee.setState(rs.getInt("state"));
            employee.setDeptId(rs.getInt("dept_id"));
            employee.setJobId(rs.getInt("job_id"));
            employee.setDeptId(rs.getInt("dept_id"));
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
