package com.xjtu.controller;

import com.xjtu.Dao.UserResposity;
import com.xjtu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserResposity userResposity;

    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable("id") Integer id){
        Optional<User> one = userResposity.findById(id);
        return one;
    }

    @GetMapping("/user")
    public User insertUser(User user){
        User save = userResposity.save(user);
        return save;
    }

}
