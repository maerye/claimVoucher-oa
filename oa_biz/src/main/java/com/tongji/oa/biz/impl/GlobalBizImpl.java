package com.tongji.oa.biz.impl;

import com.tongji.oa.biz.GlobalBiz;
import com.tongji.oa.dao.EmployeeDao;
import com.tongji.oa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalBizImpl implements GlobalBiz {
    @Autowired
    private EmployeeDao employeeDao;
    public Employee login(String sn, String password) {
        Employee employee =employeeDao.findBySn(sn);
        if(employee!=null && employee.getPassword().equals(password)){
            return employee;
        }
        return null;
    }

    public void changePassword(Employee employee) {
        employeeDao.update(employee);
    }
}
