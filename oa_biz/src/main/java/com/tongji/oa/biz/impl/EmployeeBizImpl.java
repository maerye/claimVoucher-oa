package com.tongji.oa.biz.impl;

import com.tongji.oa.biz.DepartmentBiz;

import com.tongji.oa.biz.EmployeeBiz;
import com.tongji.oa.dao.EmployeeDao;

import com.tongji.oa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeBizImpl implements EmployeeBiz {

    @Autowired
    private EmployeeDao employeeDao;

    public void add(Employee employee) {
        employee.setPassword("000000");
        employeeDao.insert(employee);
    }

    public void remove(String sn) {
        employeeDao.delete(sn);
    }

    public void edit(Employee employee) {
        employeeDao.update(employee);
    }

    public Employee get(String sn) {
        return employeeDao.findBySn(sn);
    }

    public List<Employee> getAll() {
        return employeeDao.findAll();
    }
}
