package com.github.graycat27.twitterbot.web;

import com.github.graycat27.twitterbot.twitter.api.RedirectInfo;
import com.github.graycat27.twitterbot.twitter.api.caller.SendTweetApi;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.utils.TweetTemplate;
import com.github.graycat27.twitterbot.web.service.GetAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServlet;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@SpringBootApplication
public class WebController extends HttpServlet {

    /** Spring boot用のmainメソッド */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebController.class, args);
    }

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	String index(){
		return idx();
	}
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String idx(){
        return "index";
    }

    @RequestMapping(value = "/getAuth", method = RequestMethod.GET)
    String getAuth(HttpServletRequest request, HttpServletResponse response){
        GetAuthService service = new GetAuthService();
        RedirectInfo redirectInfo = service.getAuth();

        HttpSession session = request.getSession();
        session.setAttribute("state", redirectInfo.state);
        session.setAttribute("challenge", redirectInfo.challenge);

        return "redirect:"+ redirectInfo.url.url;
    }

    @RequestMapping(value = "/twitterAuthCallback", method = RequestMethod.GET)
    String authCodeCallback(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam String state, @RequestParam String code
    ){
        GetAuthService service = new GetAuthService();
        AccessToken token = service.getUserRefreshToken(state, code,
                (UUID) request.getSession().getAttribute("state"),
                (String) request.getSession().getAttribute("challenge"));
        request.getSession().setAttribute("token", token);
        return "redirect:twitterAuthComplete";
    }

    @RequestMapping(value = "/twitterAuthComplete", method = RequestMethod.GET)
    String authCallBack(HttpServletRequest request){
        GetAuthService service = new GetAuthService();
        AccessToken token = (AccessToken) request.getSession().getAttribute("token");
        service.registerUserAccessToken(token);

        /* 登録したことをツイート */
        String status = TweetTemplate.authed;
        SendTweetApi.sendTweet(token.getId(), status);
        return "twitterAuthDone";
    }
}
