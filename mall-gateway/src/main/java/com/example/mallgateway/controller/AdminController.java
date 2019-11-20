package com.example.mallgateway.controller;

import com.example.mallgateway.config.URLWhritList;
import com.example.mallgateway.database.GatewayInterfaceBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    URLWhritList urlWhritList;

    @GetMapping("reload")
    public String reload(){
        urlWhritList.reload();
        return "ok";
    }

    @GetMapping("list")
    public Map<String, GatewayInterfaceBean> list(){
        return URLWhritList.getMaps();
    }

}
