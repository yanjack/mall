package com.example.userservice.dao;
import com.example.user.api.mode.UserBean;

import java.util.List;


public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBean record);

    int insertSelective(UserBean record);

    UserBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBean record);

    int updateByPrimaryKey(UserBean record);

    UserBean selectByPhone(String phone);

    List<UserBean> listAll();
}