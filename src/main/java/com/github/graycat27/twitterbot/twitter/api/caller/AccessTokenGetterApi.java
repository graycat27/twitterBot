package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.utils.JsonUtil;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

public class AccessTokenGetterApi {

    public static AccessToken getAccessToken() throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(ApiUrl.getAccessToken.url);
        String resJson = ApiManager.getApiCaller().callApiV1(uriBuilder);

        Type dataType = new TypeToken<ResponseCore<AccessToken>>(){}.getType();
        ResponseCore<AccessToken> data = JsonUtil.getObjectFromJsonStr(resJson, dataType);

        System.out.println("============>>>>>");
        System.out.println(data);
        System.out.println("============<<<<<");

        return data.getData();
    }
}
