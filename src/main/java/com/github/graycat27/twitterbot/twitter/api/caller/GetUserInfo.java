package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.twitter.api.GetApiAuth;
import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.UserInfoData;
import com.github.graycat27.twitterbot.utils.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GetUserInfo {

    /**
     * https://github.com/twitterdev/Twitter-API-v2-sample-code/blob/main/User-Lookup/UsersDemo.java
     * @throws URISyntaxException
     * @throws IOException
     */
    public static ResponseCore<UserInfoData> getUser() throws URISyntaxException, IOException {
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/users/466278557");

        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("user.fields", "public_metrics"));
        uriBuilder.addParameters(queryParameters);

        TwitterAuthDomain auth = GetApiAuth.getTwitterAuth();

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", auth.getBearerToken()));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();

        String responseJsonStr = null;
        if (null != entity) {
            responseJsonStr = EntityUtils.toString(entity, "UTF-8");
        }

        System.out.println("============>>>>>");
        System.out.println(responseJsonStr);
        System.out.println("============<<<<<");

        Type dataType = new TypeToken<ResponseCore<UserInfoData>>(){}.getType();
        ResponseCore<UserInfoData> data = JsonUtil.getObjectFromJsonStr(responseJsonStr, dataType);

        System.out.println("============>>>>>");
        System.out.println(data);
        System.out.println("============<<<<<");

        return data;
    }
}
