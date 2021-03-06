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
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServlet;
import java.util.Objects;

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
        RequestToken requestToken = service.getAuth();
        model.addAttribute(requestToken);
        return "redirect:https://api.twitter.com/oauth/authorize?oauth_token="+ requestToken.get("oauth_token");
    }

    @RequestMapping(value = "/twitterAuthCallback", method = RequestMethod.GET)
    String authCallBack(String oauth_token, String oauth_verifier,
                        @ModelAttribute @Validated({RequestToken.class}) RequestToken sessionToken,
                        SessionStatus sessionStatus){
        try{
            Objects.requireNonNull(oauth_token);
            Objects.requireNonNull(oauth_verifier);
        }catch(NullPointerException e){
            return idx();
        }
        sessionStatus.setComplete();    //sessionの情報削除

        if(!(sessionToken.getToken().equals(oauth_token))){
            return idx();
        }

        GetAuthService service = new GetAuthService();
        AccessToken response = service.getUserAccessToken(oauth_token, oauth_verifier);
        service.registerUserAccessToken(response);

        return "twitterAuthCallback";
    }
}
