package com.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 这是一个首页的控制器
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "index";// /WEB-INF/jsp/index.jsp
    }

    @RequestMapping("/{path}")
    public String show(@PathVariable("path") String path) {
        return path;
    }
}
