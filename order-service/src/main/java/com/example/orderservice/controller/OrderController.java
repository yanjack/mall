package com.example.orderservice.controller;

import com.example.orderservice.feign.UserFeign;
import com.example.user.api.mode.UserBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    UserFeign userFeign;

    @GetMapping("order/test")
    public UserBean test(){
        return userFeign.selectByPhone("18574635085");
    }
}
