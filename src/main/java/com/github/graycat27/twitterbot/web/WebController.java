package com.github.graycat27.twitterbot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@SpringBootApplication
public class WebController {

    /** Spring boot用のmainメソッド */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebController.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index(){
        return "index";
    }
}
