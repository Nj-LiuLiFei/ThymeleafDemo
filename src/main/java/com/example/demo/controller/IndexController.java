package com.example.demo.controller;

import com.example.demo.util.RSACoderUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }
}
