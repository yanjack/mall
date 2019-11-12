package com.example.userservice.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.user.api.mode.UserBean;
import com.example.userservice.service.UserService;

import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

//@Controller(value = "/api/user")
@RestController
@RequestMapping("user")
public class UserController  {

    @Resource
    UserService userService;

    @PostMapping(value = "/insert")
    public UserBean insert(@RequestBody UserBean userBean) {
        return userService.insert(userBean);
    }

    @GetMapping(value = "/selectByPhone")
    public UserBean selectByPhone(@RequestParam("phone") String phone) {
        return userService.selectByPhone(phone);
    }

    @GetMapping(value = "/listAll")
    public PageInfo<UserBean> listAll(@RequestParam(value = "pageNo",required = false,defaultValue = "0") Integer pageNo,
                                      @RequestParam (value = "pageSize",required = false,defaultValue = "3") Integer pageSize) {
        return userService.listAll(pageNo,pageSize);
    }
}
