package com.github.graycat27.twitterbot.twitter.api.response.data;

/**
 * アクセストークン取得時にのみ使用する使い捨てトークンデータ
 */
public class RequestToken extends IMetaData {

    private final String oauth_token;
    public String getToken(){
        return oauth_token;
    }
    private final String oauth_token_secret;
    public String getTokenSecret(){
        return oauth_token_secret;
    }

    public RequestToken(String token, String tokenSecret){
        this.oauth_token = token;
        this.oauth_token_secret = tokenSecret;
    }

}
