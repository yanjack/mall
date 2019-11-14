package com.example.userservice.controller;

import com.example.user.api.mode.dto.TokenInfoDto;
import com.example.userservice.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("user/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource
    LoginService loginService;

    @GetMapping("dologin")
    public TokenInfoDto doLogin(@RequestParam("phone") String phone){
        return loginService.dologin(phone);
    }
}
