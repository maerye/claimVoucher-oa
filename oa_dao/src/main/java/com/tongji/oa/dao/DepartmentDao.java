package com.tongji.oa.dao;

import com.tongji.oa.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentDao {

     Department findBySn(String sn);
     void insert(Department department);
     void delete(String sn);
     void update(Department department);
     List<Department> findAll();

}
