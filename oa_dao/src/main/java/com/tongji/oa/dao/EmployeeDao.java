package com.tongji.oa.dao;

import com.tongji.oa.entity.Department;
import com.tongji.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao {

    Employee findBySn(String sn);
    void insert(Employee employee);
    void delete(String sn);
    void update(Employee employee);
    List<Employee> findAll();
    List<Employee> getByDepartmentAndPos(@Param("dsn") String dsn, @Param("post") String post);
}
