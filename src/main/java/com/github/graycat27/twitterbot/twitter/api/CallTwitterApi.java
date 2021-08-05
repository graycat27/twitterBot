package com.github.graycat27.twitterbot.twitter.api;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.oauth.GetOauthHeader;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import com.github.graycat27.twitterbot.utils.JsonUtil;
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
import org.apache.http.entity.AbstractHttpEntity;
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
    public String callApiV1Post(ApiUrl.UrlString callUrl, AccessToken token,List<NameValuePair> postParam){
        URIBuilder uriBuilder;
        HttpEntity entity;
        String responseJsonStr;

        try{
            uriBuilder = new URIBuilder(callUrl.url);
            loggingStart(uriBuilder, HttpMethod.POST);

            uriBuilder.addParameters(postParam);
            HttpClient httpClient =
                    HttpClients.custom().setDefaultRequestConfig(
                            RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()
                    ).build();
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            httpPost.setHeader("Authorization", GetOauthHeader.getUserOauthHeader(token, callUrl, postParam));
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(postParam, StandardCharsets.UTF_8));
            HttpResponse response = httpClient.execute(httpPost);
            loggingApiResponse(response);
            entity = response.getEntity();

        }catch(URISyntaxException | IOException e){
            System.out.println("Exception occurred while calling Twitter API v1");
            System.out.println(e.getMessage());
            System.out.println(callUrl);
            throw new RuntimeException(e);
        }

        responseJsonStr = convertEntity2JsonStr(entity);

        System.out.println("==ApiResponse==>>>>>");
        System.out.println(responseJsonStr);
        System.out.println("==ApiResponse==<<<<<");

        loggingEnd(uriBuilder);
        return responseJsonStr;

    }

    public String callApiV1Post(URIBuilder callUrl, RequestToken token, List<NameValuePair> postParam){
        loggingStart(callUrl, HttpMethod.POST);

        HttpEntity entity;
        String responseJsonStr;
        try{
            HttpClient httpClient =
                    HttpClients.custom().setDefaultRequestConfig(
                            RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()
                    ).build();
            HttpPost httpPost = new HttpPost(callUrl.build());
            httpPost.setHeader("Authorization", GetOauthHeader.getOauthHeader(token));
            httpPost.setHeader("Content-Type", "application/json");
            if(postParam != null && postParam.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(postParam, StandardCharsets.UTF_8));
            }
            HttpResponse response = httpClient.execute(httpPost);
            loggingApiResponse(response);
            entity = response.getEntity();

        }catch(URISyntaxException | IOException e){
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
            httpGet.setHeader("Authorization", String.format("Bearer %s", authInfo.getBearerToken()));
            httpGet.setHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(httpGet);
            loggingApiResponse(response);
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

    private static void loggingStart(URIBuilder url, HttpMethod method){
        System.out.println("--- Api call start ----->");
        System.out.println("--- call URL = "+ url.getPath());
        System.out.println("--- call method = "+ method);
    }

    private static void loggingApiResponse(HttpResponse response){
        System.out.println("HttpStatus: " + response.getStatusLine().getStatusCode());
        try{
            String entityStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            System.out.println("HttpEntity: " + entityStr);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("error while parsing HttpEntity for logging");
        }
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
