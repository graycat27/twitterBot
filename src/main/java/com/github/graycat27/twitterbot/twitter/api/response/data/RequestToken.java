package com.github.graycat27.twitterbot.twitter.api.response.data;

/**
 * アクセストークン取得時にのみ使用する使い捨てトークンデータ
 */
public class RequestToken extends IMetaData implements OauthToken {

    private final String oauth_token;
    @Override
    public String getToken(){
        return oauth_token;
    }
    private final String oauth_token_secret;
    @Override
    public String getTokenSecret(){
        return oauth_token_secret;
    }
    private final String oauth_verifier;
    public String getOauthVerifier(){
        return oauth_verifier;
    }
    private final String oauth_refresh_token;
    @Override
    public String getRefreshToken() {
        return oauth_refresh_token;
    }

    public RequestToken(String token, String tokenSecret, String verifier, String refresh){
        this.oauth_token = token;
        this.oauth_token_secret = tokenSecret;
        this.oauth_verifier = verifier;
        this.oauth_refresh_token = refresh;
    }

}
