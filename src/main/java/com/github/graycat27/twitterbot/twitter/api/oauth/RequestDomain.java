package com.github.graycat27.twitterbot.twitter.api.oauth;

import org.springframework.http.HttpMethod;

public class RequestDomain {

    private final String consumerKey;
    public String getConsumerKey(){
        return consumerKey;
    }
    private final String consumerSecret;
    public String getConsumerSecret(){
        return consumerSecret;
    }
    private final String oauthToken;
    public String getOauthToken(){
        return oauthToken;
    }
    private final String oauthTokenSecret;
    public String getOauthTokenSecret(){
        return oauthTokenSecret;
    }
    private final HttpMethod method;
    public HttpMethod getMethod(){
        return method;
    }
    private final String urlStr;
    public String getUrlStr(){
        return urlStr;
    }

    public RequestDomain(
            String consumerKey, String consumerSecret,
            String oauthToken, String oauthTokenSecret,
            HttpMethod method, String urlStr
    ){
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.oauthToken = oauthToken;
        this.oauthTokenSecret = oauthTokenSecret;
        this.method = method;
        this.urlStr = urlStr;
    }
}
