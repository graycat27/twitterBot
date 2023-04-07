package com.github.graycat27.twitterbot.twitter.api;

import com.github.graycat27.twitterbot.utils.UrlString;

/** TwitterAPI のURLを定数として保持するクラス */
public class ApiUrl {

    // private field
    private static final String twitterApiCore = "https://api.twitter.com";

    private ApiUrl(){ /* インスタンス化防止 */ }

    // public field
    public static final UrlString userById = new UrlString(twitterApiCore + "/2/users/");
    public static final UrlString getRequestToken = new UrlString(twitterApiCore + "/oauth/request_token");
    public static final UrlString getAccessToken = new UrlString(twitterApiCore + "/oauth/access_token");
    public static final UrlString postTweet = new UrlString(twitterApiCore + "/2/tweets");
}
