package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import com.github.graycat27.twitterbot.utils.JsonUtil;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

public class RequestTokenGetterApi {

    /*
     * ref: https://developer.twitter.com/ja/docs/authentication/oauth-1-0a/authorizing-a-request
     * ref: https://n3104.hatenablog.com/entry/20101014/1287070373
     */
    public static ResponseCore<RequestToken> getRequestToken() throws URISyntaxException, IOException {

        URIBuilder uriBuilder = new URIBuilder(ApiUrl.getRequestToken.url);

        String resJson = ApiManager.getApiCaller().callApiV1(uriBuilder);

        Type dataType = new TypeToken<ResponseCore<RequestToken>>(){}.getType();
        ResponseCore<RequestToken> data = JsonUtil.getObjectFromJsonStr(resJson, dataType);

        System.out.println("============>>>>>");
        System.out.println(data);
        System.out.println("============<<<<<");

        return data;
    }


}
