package com.lzj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PasswordController {

    //修改密码
    @GetMapping("/pwd")
    private String test3(){
        return "commons/commons";
    }



}
