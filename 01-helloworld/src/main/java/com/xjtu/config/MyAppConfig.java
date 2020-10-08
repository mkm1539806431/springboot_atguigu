package com.xjtu.config;

import com.xjtu.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAppConfig {

    @Bean
    public HelloService helloService(){
        System.out.println("创建类helloService");
        return new HelloService();
    }
}
