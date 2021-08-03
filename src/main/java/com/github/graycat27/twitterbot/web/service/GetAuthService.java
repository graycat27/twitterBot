package com.github.graycat27.twitterbot.web.service;

import com.github.graycat27.twitterbot.twitter.api.caller.RequestTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class GetAuthService {

    public RequestToken getAuth(){
        ResponseCore<RequestToken> apiResult;
        try {
            apiResult = RequestTokenGetterApi.getRequestToken();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return apiResult.getData();
    }

}
