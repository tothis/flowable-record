package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lilei
 * @description
 * @time 2019/9/21 16:16
 */

@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "flowable/index";
    }

    @GetMapping("path/{path}")
    public String path(@PathVariable String path) {
        return "flowable/" + path;
    }

    @GetMapping("one")
    public String one() {
        return "content1";
    }

    @GetMapping("two")
    public String two() {
        return "content2";
    }
}