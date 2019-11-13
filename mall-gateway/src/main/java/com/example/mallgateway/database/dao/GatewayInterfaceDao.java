package com.example.mallgateway.database.dao;


import com.example.mallgateway.database.GatewayInterfaceBean;

import java.util.List;

public interface GatewayInterfaceDao {
    int deleteByPrimaryKey(Integer id);

    int insert(GatewayInterfaceBean record);

    int insertSelective(GatewayInterfaceBean record);

    GatewayInterfaceBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GatewayInterfaceBean record);

    int updateByPrimaryKey(GatewayInterfaceBean record);

    List<GatewayInterfaceBean> listAll();
}