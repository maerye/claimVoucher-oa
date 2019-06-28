package com.tongji.oa.biz;


import com.tongji.oa.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeBiz {
    void add(Employee employee);
    void remove(String sn);
    void edit(Employee employee);
    Employee get(String sn);
    List<Employee> getAll();

}
