package com.xjtu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
       stringRedisTemplate.opsForValue().append("stringRedisTemplate","test01");

       String msg=stringRedisTemplate.opsForValue().get("stringRedisTemplate");
        System.out.println("----------"+msg+"--------------");
    }


}
