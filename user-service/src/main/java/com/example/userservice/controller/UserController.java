package com.example.userservice.controller;



import com.example.user.api.mode.UserBean;
import com.example.userservice.service.UserService;

import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

//@Controller(value = "/api/user")
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    UserService userService;


    @PostMapping(value = "/insert")
    public UserBean insert(@RequestBody UserBean userBean) {
        return userService.insert(userBean);
    }

    @GetMapping(value = "/selectByPhone")
    public UserBean selectByPhone(@RequestParam("phone") String phone) {
//        System.out.println(request.getParameter("phone"));
        return userService.selectByPhone(phone);
    }

    @GetMapping(value = "/listAll")
    public PageInfo<UserBean> listAll(@RequestParam(value = "pageNo",required = false,defaultValue = "0") Integer pageNo,
                                      @RequestParam (value = "pageSize",required = false,defaultValue = "3") Integer pageSize,HttpServletRequest request) {

        System.out.println(request.getHeader("token"));
        System.out.println(getUserId());
        return userService.listAll(pageNo,pageSize);
    }

    @GetMapping(value = "redis")
    public String redisSet(){
        redisUtil.set("redis","hello");
        return redisUtil.get("redis").toString();
    }
    @GetMapping(value = "redis2")
    public UserBean redisSet2(){
        UserBean userBean = new UserBean();
        userBean.setCtime(new Date());
        userBean.setId(111);
        redisUtil.set("redis2",userBean);
        return (UserBean) redisUtil.get("redis2");
    }
}
