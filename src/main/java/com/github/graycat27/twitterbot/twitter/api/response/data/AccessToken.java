package com.github.graycat27.twitterbot.twitter.api.response.data;

public class AccessToken extends IMetaData implements OauthToken{

    private final String access_token;
    @Override
    public String getToken(){
        return access_token;
    }
    private final String oauth_token_secret;
    @Override
    public String getTokenSecret(){
        return oauth_token_secret;
    }
    private final String refresh_token;
    @Override
    public String getRefreshToken() {
        return refresh_token;
    }
    private String user_id;
    public void setId(String id){
        if(user_id == null){
            this.user_id = id;
        }else{
            throw new IllegalStateException("already set");
        }
    }
    public String getId(){
        return user_id;
    }
    private final String screen_name;
    public String getScreenName(){
        return screen_name;
    }

    public AccessToken(String token, String tokenSecret, String refreshToken, String userId, String screenName){
        this.access_token = token;
        this.oauth_token_secret = tokenSecret;
        this.refresh_token = refreshToken;
        this.user_id = userId;
        this.screen_name = screenName;
    }
}
