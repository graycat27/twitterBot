package com.github.graycat27.twitterbot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Locale;

@Controller
@SpringBootApplication
public class WebController {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebController.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index(Locale locale, Model model, Principal p){
        model.addAttribute("sample", "this is sample");

        return "index.jsp";
    }
}
