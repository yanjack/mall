package com.example.userservice.controller;



import com.example.mallcommon.exception.BaseException;
import com.example.user.api.mode.UserBean;
import com.example.userservice.service.UserService;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

//@Controller(value = "/api/user")
@RestController
@RequestMapping("user")
@Api(value = "UserController",tags = "用户")
public class UserController extends BaseController {

    @Resource
    UserService userService;

    @Value("${spring.redis.port}")
    String redisPort;

    @PostMapping(value = "/insert")
    @ApiOperation("新增用户")
    public UserBean insert(@RequestBody @ApiParam(value = "json",name = "UserBean") UserBean userBean) {
        return userService.insert(userBean);
    }

    @GetMapping(value = "/selectByPhone")
    @ApiOperation("根据电话查询")
    @ApiImplicitParam(value = "phone",name = "dianhua",dataType = "String",paramType = "query")
    public UserBean selectByPhone(@RequestParam("phone") String phone) throws BaseException {
//        System.out.println(request.getParameter("phone"));
        ttt();
        System.out.println(redisPort);
        if(1==1){
            throw new BaseException("1004","异常测试");
        }
        return userService.selectByPhone(phone);
    }

    private void ttt(){
        System.out.println("11111111111");
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
