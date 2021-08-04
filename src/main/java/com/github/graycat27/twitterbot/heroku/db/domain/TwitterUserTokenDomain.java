package com.github.graycat27.twitterbot.heroku.db.domain;

import com.google.gson.Gson;

public class TwitterUserTokenDomain extends CommonDomain {

    private final String oauthToken;
    public String getOauthToken(){
        return oauthToken;
    }
    private final String oauthTokenSecret;
    public String getOauthTokenSecret(){
        return oauthTokenSecret;
    }

    public TwitterUserTokenDomain(
            String oauthToken,
            String oauthTokenSecret
    ){
        this.oauthToken = oauthToken;
        this.oauthTokenSecret = oauthTokenSecret;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
