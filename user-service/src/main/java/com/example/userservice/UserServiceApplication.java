package com.example.userservice;

import com.example.user.api.mode.UserBean;
import com.example.userservice.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(value = "com.example.userservice.dao")
@RestController
@ComponentScan(basePackages = "com.example.*")
//@ComponentScan(value ="com.example.userservice.*" )
public class UserServiceApplication {

    @Resource
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
    @GetMapping("index")
    String index(){
        return "index";
    }

    @GetMapping(value = "/selectByPhone2")
    @ResponseBody
    public UserBean selectByPhone2(@RequestParam("phone") String phone) {
        return userService.selectByPhone(phone);
    }
}
