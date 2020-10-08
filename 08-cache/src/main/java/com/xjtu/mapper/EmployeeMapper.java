package com.xjtu.mapper;

import com.xjtu.bean.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface EmployeeMapper {
    @Select("select * from employee where id=#{id}")
    public Employee getEmpById(Integer id);

    @Update("update employee set lastName=#{lastName}, email=#{email}, gender=#{gender},d_id=#{dId} where id=#{id}")
    public void updateEmp(Employee employee);
    @Delete("delete from employee where id=#{id}")
    public void delEmpById(Integer id);

}
