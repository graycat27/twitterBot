package com.github.graycat27.twitterbot.web;

import com.github.graycat27.twitterbot.twitter.api.caller.RequestTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.web.service.GetAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServlet;

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
        RequestTokenGetterApi.RedirectInfo redirectInfo = service.getAuth();

        HttpSession session = request.getSession();
        session.setAttribute("state", redirectInfo.state);
        session.setAttribute("challenge", redirectInfo.challenge);

        return "redirect:"+ redirectInfo.url.url;
    }

    @RequestMapping(value = "/twitterAuthCallback", method = RequestMethod.GET)
    String authCodeCallback(HttpServletRequest request, HttpServletResponse response, String state, String code){
        GetAuthService service = new GetAuthService();
        service.getUserAccessToken(state, code,
                (UUID) request.getSession().getAttribute("state"),
                (UUID) request.getSession().getAttribute("challenge"));

        return "working";
    }

    @RequestMapping(value = "/twitterAuthComplete", method = RequestMethod.GET)
    String authCallBack(@ModelAttribute @Validated({AccessToken.class}) AccessToken accessToken){
        GetAuthService service = new GetAuthService();
        service.registerUserAccessToken(accessToken);

        return "twitterAuthDone";
    }
}
