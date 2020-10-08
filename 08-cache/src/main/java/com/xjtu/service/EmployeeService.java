package com.xjtu.service;
import com.xjtu.bean.Employee;
import com.xjtu.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Cacheable(value = {"emp"},condition = "#p0>=1" ,unless="#p0=2", key = "#id")
    public Employee getEmp(Integer id){
        System.out.println("查询员工: "+id);
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    @CachePut(value = {"emp"},key="#id" )
    public Employee updateEmp(Employee employee){
        System.out.println("更新员工id: "+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }
    @CacheEvict(value = {"emp"},allEntries = true)

    public Integer delEmp(Integer id){
        System.out.println("删除员工id: "+id);
        employeeMapper.delEmpById(id);
        return id;
    }

}
