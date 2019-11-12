package com.example.userservice.service;

import com.example.user.api.mode.UserBean;
import com.example.userservice.dao.UserDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Resource
    UserDao userDao;

    public UserBean insert (UserBean userBean){
        userBean.setCtime(new Date());
        userDao.insert(userBean);
        return userBean;
    }

    public UserBean selectByPhone (String phone){
        return userDao.selectByPhone(phone);
    }

    public PageInfo<UserBean> listAll(int pageNo,int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<UserBean> res = userDao.listAll();
        PageInfo <UserBean> pageInfo = new PageInfo<>(res);
        return pageInfo;
    }
}
