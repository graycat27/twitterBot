package com.github.graycat27.twitterbot.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Locale;

public class WebController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Locale locale, Model model, Principal p){
        model.addAttribute("sample", "this is sample");

        return "index";
    }
}
