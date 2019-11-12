package com.example.mallweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @GetMapping("index")
    public String index(){
        return "index";
    }
    @GetMapping("index2")
    @ResponseBody
    public String index2(){
        return "index2";
    }
}
