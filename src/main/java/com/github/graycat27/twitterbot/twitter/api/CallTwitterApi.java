package com.github.graycat27.twitterbot.twitter.api;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.oauth.GetOauthHeader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class CallTwitterApi {

    // field
    private final TwitterAuthDomain authInfo;


    // constructor
    public CallTwitterApi(){
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        authInfo = authQuery.selectOne(null);
    }

    // method
    public String callApiV1(URIBuilder callUrl){
        HttpEntity entity;
        String responseJsonStr;
        try{
            HttpClient httpClient =
                    HttpClients.custom().setDefaultRequestConfig(
                            RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()
                    ).build();
            HttpPost httpPost = new HttpPost(callUrl.build());
            httpPost.setHeader("Authorization", GetOauthHeader.getOauthHeader());
            httpPost.setHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(httpPost);
            entity = response.getEntity();

        }catch(URISyntaxException | IOException e){
            System.out.println("Exception occurred while calling Twitter API v2");
            System.out.println(e.getMessage());
            System.out.println(callUrl);
            throw new RuntimeException(e);
        }

        responseJsonStr = convertEntity2JsonStr(entity);

        System.out.println("==ApiResponse==>>>>>");
        System.out.println(responseJsonStr);
        System.out.println("==ApiResponse==<<<<<");

        return responseJsonStr;

    }

    public String callApiV2(URIBuilder callUrl){
        HttpEntity entity;
        String responseJsonStr;
        try {
            HttpClient httpClient =
                    HttpClients.custom().setDefaultRequestConfig(
                            RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()
                    ).build();
            HttpGet httpGet = new HttpGet(callUrl.build());
            httpGet.setHeader("Authorization", String.format("Bearer %s", authInfo.getBearerToken()));
            httpGet.setHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(httpGet);
            entity = response.getEntity();
        }catch(URISyntaxException | IOException e){
            System.out.println("Exception occurred while calling Twitter API v2");
            System.out.println(e.getMessage());
            System.out.println(callUrl);
            throw new RuntimeException(e);
        }
        responseJsonStr = convertEntity2JsonStr(entity);

        System.out.println("==ApiResponse==>>>>>");
        System.out.println(responseJsonStr);
        System.out.println("==ApiResponse==<<<<<");

        return responseJsonStr;
    }

    private static String convertEntity2JsonStr(HttpEntity entity) {
        String jsonStr = null;
        try {
            if (null != entity) {
                jsonStr = EntityUtils.toString(entity, StandardCharsets.UTF_8.name());
            }
        } catch (IOException e) {
            System.out.println("Exception occurred while converting API response data");
            System.out.println(e.getMessage());
            System.out.println(entity);
            throw new RuntimeException(e);
        }

        return jsonStr;
    }
}
