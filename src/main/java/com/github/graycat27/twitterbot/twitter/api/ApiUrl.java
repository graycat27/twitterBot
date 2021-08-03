package com.github.graycat27.twitterbot.twitter.api;

/** TwitterAPI のURLを定数として保持するクラス */
public class ApiUrl {

    // private field
    private static final String twitterApiCore = "https://api.twitter.com";

    private ApiUrl(){ /* インスタンス化防止 */ }

    public static class UrlString{
        public final String url;
        public UrlString(String url){
            this.url = url;
        }
        public String toString(){
            return url;
        }
    }

    // public field
    public static final UrlString userById = new UrlString(twitterApiCore + "/2/users/");
}
