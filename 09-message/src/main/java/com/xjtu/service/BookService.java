package com.xjtu.service;

import com.xjtu.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class BookService {
    @RabbitListener(queues = "atguigu.news")
    public void receive(Book book){
        System.out.println("收到消息："+book);
    }

    @RabbitListener(queues = "atguigu")
    public void receive(Message message){
        System.out.println("收到消息"+message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
