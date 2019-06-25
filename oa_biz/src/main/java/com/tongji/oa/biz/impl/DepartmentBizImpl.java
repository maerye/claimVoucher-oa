package com.tongji.oa.biz.impl;

import com.tongji.oa.biz.DepartmentBiz;
import com.tongji.oa.dao.DepartmentDao;
import com.tongji.oa.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentBizImpl implements DepartmentBiz {

    @Autowired
    private DepartmentDao departmentDao;

    public void add(Department department) {
         departmentDao.insert(department);
    }

    public void remove(String sn) {
         departmentDao.delete(sn);
    }

    public void edit(Department department) {
         departmentDao.update(department);
    }

    public Department get(String sn) {
        return departmentDao.findBySn(sn);
    }

    public List<Department> getAll() {
        return departmentDao.findAll();
    }
}
