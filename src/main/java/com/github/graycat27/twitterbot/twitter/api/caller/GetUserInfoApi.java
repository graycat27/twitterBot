package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.UserInfoData;
import com.github.graycat27.twitterbot.utils.JsonUtil;
import com.google.gson.reflect.TypeToken;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GetUserInfoApi {

    /**
     * https://github.com/twitterdev/Twitter-API-v2-sample-code/blob/main/User-Lookup/UsersDemo.java
     * @throws URISyntaxException
     * @throws IOException
     */
    public static ResponseCore<UserInfoData> getUser(String id) throws URISyntaxException, IOException {

        URIBuilder uriBuilder = new URIBuilder(ApiUrl.userById + id);

        ArrayList<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("user.fields", "public_metrics"));
        uriBuilder.addParameters(queryParameters);

        String resJson = ApiManager.getApiCaller().callApiV2(uriBuilder);

        Type dataType = new TypeToken<ResponseCore<UserInfoData>>(){}.getType();
        ResponseCore<UserInfoData> data = JsonUtil.getObjectFromJsonStr(resJson, dataType);

        System.out.println("============>>>>>");
        System.out.println(data);
        System.out.println("============<<<<<");

        return data;
    }
}
