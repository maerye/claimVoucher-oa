package com.tongji.oa.controller;

import com.tongji.oa.biz.DepartmentBiz;
import com.tongji.oa.biz.EmployeeBiz;
import com.tongji.oa.entity.Department;
import com.tongji.oa.entity.Employee;
import com.tongji.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private DepartmentBiz departmentBiz;

    @Autowired
    private EmployeeBiz employeeBiz;

    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",employeeBiz.getAll());
        return "employee_list";
    }

    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object>map){
        map.put("employee",new Employee());
        map.put("dlist",departmentBiz.getAll());
        map.put("plist",Contant.getPost());
        return "employee_add";
    }

    @RequestMapping("/add")
    public String add(Employee employee){
        employeeBiz.add(employee);
        return "redirect:list";
    }


    @RequestMapping(value = "/to_update{sn}")
    public String toUpdate(@PathVariable("sn") String sn, Map<String,Object> map){
        map.put("employee",employeeBiz.get(sn));
        map.put("dlist",departmentBiz.getAll());
        map.put("plist",Contant.getPost());
        return "employee_update";
    }

    @RequestMapping("/update")
    public String update(Employee employee){
        employeeBiz.edit(employee);
        return "redirect:list";
    }

    @RequestMapping("/remove{sn}")
    public String remove(@PathVariable("sn") String sn){
        employeeBiz.remove(sn);
        return "redirect:list";
    }
}
