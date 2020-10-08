package com.xjtu.controller;

import com.xjtu.dao.DepartmentDao;
import com.xjtu.dao.EmployeeDao;
import com.xjtu.domain.Department;
import com.xjtu.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    // 查询所有员工
    @GetMapping(value = "/emps")
    public String list(Model model){
        Collection<Employee> employeeDaoAll = employeeDao.getAll();
        model.addAttribute("emps",employeeDaoAll);
        return "emp/list";
    }


    //来到员工添加页面
    @GetMapping(value = "/emp")
    public String empAddPage(Model model){
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }

    //新增员工
    @PostMapping(value = "/emp")
    public String add(Employee employee){
        System.out.println(employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //删除员工
    @DeleteMapping(value = "/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }


    //来到修改页面

    @GetMapping("/emp/{id}")
    public String toUpdatePage(@PathVariable("id")Integer id, Model model){

        Employee employee=employeeDao.get(id);
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        model.addAttribute("emp",employee);
        return "emp/add";
    }

    //修改员工
    @PutMapping(value = "/emp")
    public String put(Employee employee){
        employeeDao.save(employee);
        System.out.println("修改的员工数据："+employee);
        return "redirect:/emps";
    }




}
