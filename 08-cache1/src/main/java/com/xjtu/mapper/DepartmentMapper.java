package com.xjtu.mapper;

import com.xjtu.bean.Department;
import org.apache.ibatis.annotations.Select;

public interface DepartmentMapper {
    @Select("select * from department where id =#{id}")
    public Department getDepartmentById(Integer id);
}
