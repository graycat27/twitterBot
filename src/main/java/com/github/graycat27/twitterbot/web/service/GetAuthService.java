package com.github.graycat27.twitterbot.web.service;

import com.github.graycat27.twitterbot.twitter.api.caller.AccessTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.caller.RequestTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class GetAuthService {

    public RequestToken getAuth(){
        RequestToken apiResult;
        try {
            apiResult = RequestTokenGetterApi.getRequestToken();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return apiResult;
    }

    public AccessToken getUserAccessToken(String token, String verifier){

        AccessToken apiResult;
        try{
            apiResult = AccessTokenGetterApi.getAccessToken(token, verifier);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return apiResult;
    }

}
