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
        String str1="LiuLiFei246199";
        String encrypt= RSACoderUtil.encryptByPublicKey(str1);
        System.out.println("原数据："+str1);
        System.out.println("加密之后的数据："+encrypt);
        System.out.println("解密之后的数据："+RSACoderUtil.decryptByPrivateKey(encrypt));
        return "index";
    }
}
