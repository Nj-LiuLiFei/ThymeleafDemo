package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping(value = "service/api/",produces="text/javascript;charset=UTF-8")
public class AuthorizationController {

    @GetMapping("/getAuth")
    public String getAuth(){
        return "var auth = \"liulifei\";";

    }
}
