package com.github.graycat27.twitterbot.twitter.api;

import com.github.graycat27.twitterbot.utils.UrlString;

/** TwitterAPI のURLを定数として保持するクラス */
public class ApiUrl {

    // private field
    private static final String twitterApiCore = "https://api.twitter.com";
    private static final String twitterCore = "https://twitter.com";

    private ApiUrl(){ /* インスタンス化防止 */ }

    // public field
    public static final UrlString userById = new UrlString(twitterApiCore + "/2/users/");
    public static final UrlString getAuthorize = new UrlString(twitterCore + "/i/oauth2/authorize");
    public static final UrlString getAccessToken = new UrlString(twitterApiCore + "/oauth2/token");
    public static final UrlString postTweet = new UrlString(twitterApiCore + "/2/tweets");
}
