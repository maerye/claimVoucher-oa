package com.tongji.oa.controller;

import com.tongji.oa.biz.DepartmentBiz;
import com.tongji.oa.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentBiz departmentBiz;

    @RequestMapping("/list")
    public String list(Map<String,Object> map){

        map.put("list",departmentBiz.getAll());
        return "department_list";
    }

    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object>map){
        map.put("department",new Department());
        return "department_add";
    }

    @RequestMapping("/add")
    public String add(Department department){
        departmentBiz.add(department);
        return "redirect:list";
    }


    @RequestMapping(value = "/to_update{sn}")
    public String toUpdate(@PathVariable("sn") String sn, Map<String,Object> map){
        map.put("department",departmentBiz.get(sn));
        return "department_update";
    }

    @RequestMapping("/update")
    public String update(Department department){
        departmentBiz.edit(department);
        return "redirect:list";
    }

    @RequestMapping("/remove{sn}")
    public String remove(@PathVariable("sn") String sn){
        departmentBiz.remove(sn);
        return "redirect:list";
    }
}
