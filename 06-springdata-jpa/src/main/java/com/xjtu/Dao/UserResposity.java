package com.xjtu.Dao;

import com.xjtu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResposity extends JpaRepository<User,Integer> {

}
