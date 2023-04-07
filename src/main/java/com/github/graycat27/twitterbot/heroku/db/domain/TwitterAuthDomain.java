package com.github.graycat27.twitterbot.heroku.db.domain;

import com.github.graycat27.twitterbot.utils.JsonUtil;

public class TwitterAuthDomain extends CommonDomain {

    private String apiKey;
    public String getApiKey(){
        return apiKey;
    }
    private String secretKey;
    public String getSecretKey(){
        return secretKey;
    }
    private String bearerToken;
    public String getBearerToken(){
        return bearerToken;
    }
    private String clientId;
    public String getClientId(){
        return clientId;
    }
    private String clientSecret;
    public String getClientSecret(){
        return clientSecret;
    }

    public TwitterAuthDomain(
        String apiKey,
        String secretKey,
        String bearerToken,
        String clientId,
        String clientSecret
    ){
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.bearerToken = bearerToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
    public TwitterAuthDomain(){
        //nothing to do
    }

    @Override
    public String toString() {
        return JsonUtil.getJsonString(this);
    }
}
