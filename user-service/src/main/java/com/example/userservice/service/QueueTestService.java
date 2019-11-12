package com.example.userservice.service;

import com.example.mallmq.MQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QueueTestService {

    private static final Logger logger = LoggerFactory.getLogger(QueueTestService.class);

    @Resource
    MQService mqService;

    public void send(){
        for(int i =0;i<20;i++){
            mqService.sendByDirectExchange("queuetest2","msg"+i);
            logger.info("发送消息：msg"+i);
        }
    }

    @RabbitHandler
    @RabbitListener(queues = "queuetest2")
    public void test2(String msg){
        logger.info("1收到消息"+msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "queuetest2")
    public void test3(String msg){
        logger.info("2收到消息"+msg);
    }


}
