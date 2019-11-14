package com.example.userservice.controller;

import com.example.mallcache.RedisUtil;
import com.example.user.api.mode.UserBean;
import com.example.userservice.constant.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class BaseController {

    private static final String TOKEN = "token";

    @Autowired
    HttpServletRequest request;

    @Resource
    RedisUtil redisUtil;

    public UserBean getUserBean(){
        return (UserBean) redisUtil.get(RedisConstants.TOKEN +request.getHeader(TOKEN));
    }

    public Integer getUserId(){
        return getUserBean().getId();
    }

}
