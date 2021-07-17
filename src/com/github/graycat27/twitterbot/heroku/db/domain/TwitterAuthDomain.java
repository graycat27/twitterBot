package com.github.graycat27.twitterbot.heroku.db.domain;

public class TwitterAuthDomain {

    public final String apiKey;
    public final String secretKey;
    public final String bearerToken;

    public TwitterAuthDomain(
        String apiKey,
        String secretKey,
        String bearerToken
    ){
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.bearerToken = bearerToken;
    }

}
