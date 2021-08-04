package com.github.graycat27.twitterbot.web;

import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import com.github.graycat27.twitterbot.web.service.GetAuthService;
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

    @RequestMapping(value = "/getAuth", method = RequestMethod.GET)
    String getAuth(){
        GetAuthService service = new GetAuthService();
        RequestToken requestToken = service.getAuth();
        return "redirect:https://api.twitter.com/oauth/authorize?oauth_token="+ requestToken.get("oauth_token");
    }

    @RequestMapping(value = "/twitterAuthCallback", method = RequestMethod.GET)
    String authCallBack(String oauth_token, String oauth_verifier){
        GetAuthService service = new GetAuthService();
        AccessToken response = service.getUserAccessToken(oauth_token, oauth_verifier);

        //TODO make this
        System.out.println(response);
        /*
         * AccessToken からIDを取得し、DBのマスタ登録をする
         */
        return "twitterAuthCallback";
    }
}
