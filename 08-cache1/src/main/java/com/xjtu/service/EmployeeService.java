package com.xjtu.service;
import com.xjtu.bean.Employee;
import com.xjtu.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@Service
@CacheConfig
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

//    @Cacheable(value = {"emp"},key = "#id"/*,condition = "#p0>=1" ,unless="#p0=2"*/)

    @Cacheable(value = {"emp"},keyGenerator = "myKeyGenerator")
    public Employee getEmp(Integer id){
        System.out.println("查询员工: "+id);
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    @CachePut(value = {"emp"},key="#employee.id" )
    public Employee updateEmp(Employee employee){
        System.out.println("更新员工id: "+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }
    @CacheEvict(value = {"emp"},allEntries = true,beforeInvocation = true)
    public Integer delEmp(Integer id){

        System.out.println("删除员工id: "+id);
        int i=1/0;
        employeeMapper.delEmpById(id);
        return id;
    }

    @Caching(
        cacheable={
                @Cacheable(value={"emp"},key = "#lastName")/*,
                @Cacheable(value={"emp"},key = "#result.email")*/
        }
    )
    public Employee getEmpByLastName(String lastName){
        System.out.println("员工名字："+lastName);
        return employeeMapper.getEmpByLastName(lastName);
    }
    public Employee getEmpByEmail(String email){
        System.out.println("员工email："+email);
        return employeeMapper.getEmpByEmail(email);
    }
}
