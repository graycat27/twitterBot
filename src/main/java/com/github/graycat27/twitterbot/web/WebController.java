package com.github.graycat27.twitterbot.web;

import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import com.github.graycat27.twitterbot.web.service.GetAuthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import jakarta.servlet.http.HttpServlet;

@Controller
@SpringBootApplication
@SessionAttributes(types = {RequestToken.class})
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
    String getAuth(Model model){
        GetAuthService service = new GetAuthService();
        return service.getAuth().url;

    }

    @RequestMapping(value = "/twitterAuthCallback", method = RequestMethod.GET)
    String authCodeCallback(String state, String code){
        GetAuthService service = new GetAuthService();
        service.getUserAccessToken(state, code);

        return "working";
    }

    @RequestMapping(value = "/twitterAuthComplete", method = RequestMethod.GET)
    String authCallBack(@ModelAttribute @Validated({AccessToken.class}) AccessToken accessToken){
        GetAuthService service = new GetAuthService();
        service.registerUserAccessToken(accessToken);

        return "twitterAuthDone";
    }
}
