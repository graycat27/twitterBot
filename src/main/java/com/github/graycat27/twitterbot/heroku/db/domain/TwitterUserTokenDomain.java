package com.github.graycat27.twitterbot.heroku.db.domain;

import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.google.gson.Gson;

import java.sql.Timestamp;

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
    public TwitterUserTokenDomain(String twUserId){
        this.twUserId = twUserId;
        this.oauthToken = null;
        this.oauthTokenSecret = null;
        this.oauthRefreshToken = null;
    }
    /** DB用 */
    public TwitterUserTokenDomain(Timestamp t1, Timestamp t2, boolean del,
                                  String oauthRefreshToken,String oauthToken,String oauthTokenSecret,String twUserId){
        this(twUserId, oauthToken, oauthTokenSecret, oauthRefreshToken);
    }

    public AccessToken getToken(){
        return new AccessToken(oauthToken,oauthTokenSecret,oauthRefreshToken,twUserId,null);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
