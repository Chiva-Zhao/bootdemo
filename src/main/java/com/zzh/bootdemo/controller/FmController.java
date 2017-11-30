package com.zzh.bootdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("fm")
@Controller
public class FmController {
    @Value("${name:我的第一个boot}")
    private String name;
    @RequestMapping("first")
    public String firstPage(Model model) {
        model.addAttribute("user","chiva");
        model.addAttribute("url","http://www.baidu.com");
        model.addAttribute("name",name);
        return "first";
    }


}
