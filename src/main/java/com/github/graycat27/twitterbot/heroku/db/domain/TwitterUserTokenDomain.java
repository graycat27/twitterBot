package com.github.graycat27.twitterbot.heroku.db.domain;

import com.google.gson.Gson;

public class TwitterUserTokenDomain extends CommonDomain {

    private final String twUserId;
    public String getTwUserId(){
        return twUserId;
    }
    private final String oauthToken;
    public String getOauthToken(){
        return oauthToken;
    }
    private final String oauthTokenSecret;
    public String getOauthTokenSecret(){
        return oauthTokenSecret;
    }
    private final String oauthRefreshToken;
    public String getOauthRefreshToken(){
        return oauthRefreshToken;
    }

    public TwitterUserTokenDomain(
            String twUserId,
            String oauthToken,
            String oauthTokenSecret,
            String oauthRefreshToken
    ){
        this.twUserId = twUserId;
        this.oauthToken = oauthToken;
        this.oauthTokenSecret = oauthTokenSecret;
        this.oauthRefreshToken = oauthRefreshToken;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
