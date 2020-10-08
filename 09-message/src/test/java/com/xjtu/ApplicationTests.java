package com.xjtu;

import com.xjtu.bean.Book;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class ApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void createExchange(){

		amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
		System.out.println("创建完成");

		amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));
//        创建绑定规则

		amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,"amqpadmin.exchange","amqp.haha",null));


    }

    @Test
    void contextLoads() {
        Map<String,Object> map=new HashMap<>();
        map.put("key1","value1");
        map.put("key2", Arrays.asList("hello",12,true));
        rabbitTemplate.convertAndSend("exchange.direct","atguigu",map);
        rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",new Book("西游记","asd"));
    }

    @Test
    void getConetxt(){
        Object o = rabbitTemplate.receiveAndConvert("atguigu.news");
        System.out.println(o);
    }

}
