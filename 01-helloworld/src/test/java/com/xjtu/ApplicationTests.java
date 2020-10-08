package com.xjtu;

import com.xjtu.bean.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;


@SpringBootTest
class ApplicationTests {

    @Autowired
    Person person;

    @Autowired
    ApplicationContext ac;
    @Test
    void contextLoads() {
        System.out.println(person);
    }
    @Test
    void contextLoads2() {
        boolean b=ac.containsBean("helloService");
        System.out.println(b);

    }
}
