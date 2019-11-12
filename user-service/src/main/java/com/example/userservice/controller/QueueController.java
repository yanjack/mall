package com.example.userservice.controller;

import com.example.mallmq.MQService;
import com.example.userservice.service.QueueTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class QueueController {
    private static final Logger logger = LoggerFactory.getLogger(QueueController.class);

    @Resource
    QueueTestService queueTestService;

    @GetMapping("test")
    public String test(){
        queueTestService.send();
        return "ok";
    }


}
