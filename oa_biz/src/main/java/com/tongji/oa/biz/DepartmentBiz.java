package com.tongji.oa.biz;

import com.tongji.oa.entity.Department;

import java.util.List;

public interface DepartmentBiz {
    void add(Department department);
    void remove(String sn);
    void edit(Department department);
    Department get(String sn);
    List<Department> getAll();
}
