package com.xjtu.controller;

import com.xjtu.bean.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
   @GetMapping("/hello/{a}/{b}")
   @ApiOperation("hello控制类")
   @ResponseBody
   public String hello(@PathVariable("a") @ApiParam("第一个数字") int a,@PathVariable("b") @ApiParam("第二个数字")int b){
       return ""+(a+b);
   }

   @GetMapping("/user")
   @ResponseBody
    public User user(){
       return new User("asd","asdfa");
   }



}
