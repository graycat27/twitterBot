package com.github.graycat27.twitterbot.twitter.api;

public class ApiManager {

    private static final CallTwitterApi callApi;

    static{
        callApi = new CallTwitterApi();
    }
    private ApiManager(){ /* インスタンス化防止 */ }

    public static CallTwitterApi getApiCaller(){
        return callApi;
    }
}
