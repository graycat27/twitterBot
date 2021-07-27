package com.github.graycat27.twitterbot.heroku.db.domain;

import com.google.gson.Gson;

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

    public TwitterAuthDomain(
        String apiKey,
        String secretKey,
        String bearerToken
    ){
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.bearerToken = bearerToken;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
