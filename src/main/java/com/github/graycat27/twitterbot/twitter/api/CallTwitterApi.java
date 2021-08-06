package com.github.graycat27.twitterbot.twitter.api;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.oauth.GetOauthHeader;
import com.github.graycat27.twitterbot.twitter.api.response.data.OauthToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CallTwitterApi {

    // field
    private final TwitterAuthDomain authInfo;


    // constructor
    public CallTwitterApi(){
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        authInfo = authQuery.selectOne(null);
    }

    // method
    public String callApiV1Post(ApiUrl.UrlString callUrl, OauthToken token, List<NameValuePair> postParam){
        HttpEntity entity;
        String responseJsonStr;

        try{
            loggingStart(callUrl, HttpMethod.POST);

//            if(postParam != null) {
//                uriBuilder.addParameters(postParam);
//            }
            HttpClient httpClient =
                    HttpClients.custom().setDefaultRequestConfig(
                            RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()
                    ).build();
            HttpPost httpPost = new HttpPost(callUrl.url);
            httpPost.addHeader("Authorization", GetOauthHeader.getOauthHeader(token, callUrl, postParam));
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            if(postParam != null){
                httpPost.setEntity(new UrlEncodedFormEntity(postParam, StandardCharsets.UTF_8));
            }
            HttpResponse response = httpClient.execute(httpPost);
            entity = response.getEntity();

        }catch(IOException e){
            System.out.println("Exception occurred while calling Twitter API v1");
            System.out.println(e.getMessage());
            System.out.println(callUrl);
            throw new RuntimeException(e);
        }

        responseJsonStr = convertEntity2JsonStr(entity);

        System.out.println("==ApiResponse==>>>>>");
        System.out.println(responseJsonStr);
        System.out.println("==ApiResponse==<<<<<");

        loggingEnd(callUrl);
        return responseJsonStr;

    }

    public String callApiV2Get(URIBuilder callUrl){
        loggingStart(callUrl, HttpMethod.GET);

        HttpEntity entity;
        String responseJsonStr;
        try {
            HttpClient httpClient =
                    HttpClients.custom().setDefaultRequestConfig(
                            RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()
                    ).build();
            HttpGet httpGet = new HttpGet(callUrl.build());
            httpGet.addHeader("Authorization", String.format("Bearer %s", authInfo.getBearerToken()));
            httpGet.addHeader("Content-Type", "application/json");

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

        loggingEnd(callUrl);
        return responseJsonStr;
    }

    private static void loggingStart(ApiUrl.UrlString url, HttpMethod method){
        System.out.println("--- Api call start ----->");
        System.out.println("--- call URL = "+ url.url);
        System.out.println("--- call method = "+ method);
    }

    private static void loggingStart(URIBuilder url, HttpMethod method){
        System.out.println("--- Api call start ----->");
        System.out.println("--- call URL = "+ url.getPath());
        System.out.println("--- call method = "+ method);
    }

    private static void loggingEnd(ApiUrl.UrlString url){
        System.out.println("--- called URL = "+ url.url);
        System.out.println("--- Api call end -----<");
    }

    private static void loggingEnd(URIBuilder url){
        System.out.println("--- called URL = "+ url.getPath());
        System.out.println("--- Api call end -----<");
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
