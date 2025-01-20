package com.github.graycat27.twitterbot.twitter.api;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.oauth.GetOauthHeader;
import com.github.graycat27.twitterbot.twitter.api.oauth.GetV2OauthHeader;
import com.github.graycat27.twitterbot.twitter.api.response.data.OauthToken;
import com.github.graycat27.twitterbot.utils.UrlString;
import com.github.graycat27.twitterbot.utils.exception.TwitterApiException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
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
    public String callApiV1Post(UrlString callUrl, OauthToken token, List<NameValuePair> postParam){
        HttpEntity entity;
        String responseJsonStr;

        loggingStart(callUrl, HttpMethod.POST);
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost(callUrl.url);
            httpPost.addHeader("Authorization", GetOauthHeader.getOauthHeader(token, callUrl, HttpMethod.POST, postParam));
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            if(postParam != null){
                httpPost.setEntity(new UrlEncodedFormEntity(postParam, StandardCharsets.UTF_8));
            }
            HttpEntity responseEntity = httpClient.execute(httpPost, response ->{
                if(response.getCode() != HttpStatus.SC_OK){
                    System.err.println("Response status code = "+ response.getCode());
                    throw new TwitterApiException("API response status code was not 200-OK");
                }
                return response.getEntity();
            });
            responseJsonStr = convertEntity2JsonStr(responseEntity);

        }catch(IOException e){
            System.err.println("Exception occurred while calling Twitter API v1");
            System.err.println(e.getMessage());
            System.err.println(callUrl);
            throw new TwitterApiException(e);
        }

        System.out.println("==ApiResponse==>>>>>");
        System.out.println(responseJsonStr);
        System.out.println("==ApiResponse==<<<<<");

        loggingEnd(callUrl);
        return responseJsonStr;

    }

    public String callApiV2Get(UrlString baseUrl, URIBuilder callUrl){
        loggingStart(callUrl, HttpMethod.GET);

        HttpEntity entity;
        String responseJsonStr;
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpGet httpGet = new HttpGet(callUrl.build());
            httpGet.addHeader("Authorization", GetOauthHeader.getOauthHeader(null, baseUrl, HttpMethod.GET, Collections.emptyList()));
            httpGet.addHeader("Content-Type", "application/json");

            HttpEntity responseEntity = httpClient.execute(httpGet, response -> {
                if(response.getCode() != HttpStatus.SC_OK){
                    System.err.println("Response status code = "+ response.getCode());
                    throw new TwitterApiException("API response status code was not 200-OK");
                }
                return response.getEntity();
            });
            responseJsonStr = convertEntity2JsonStr(responseEntity);
        }catch(URISyntaxException | IOException e){
            System.err.println("Exception occurred while calling Twitter API v2");
            System.err.println(e.getMessage());
            System.err.println(callUrl);
            throw new TwitterApiException(e);
        }

        System.out.println("==ApiResponse==>>>>>");
        System.out.println(responseJsonStr);
        System.out.println("==ApiResponse==<<<<<");

        loggingEnd(callUrl);
        return responseJsonStr;
    }

    public String callApiV2PostUrlEncodedContent(UrlString callUrl, List<NameValuePair> postParam){
        return callApiV2Post(callUrl, GetV2OauthHeader.getAuthorizationHeader(), postParam, "application/x-www-form-urlencoded");
    }
    public String callApiV2PostUrlEncodedContent(UrlString callUrl, OauthToken token, List<NameValuePair> postParam){
        return callApiV2Post(callUrl, GetV2OauthHeader.getAuthorizationHeader(token), postParam, "application/x-www-form-urlencoded");
    }

    public String callApiV2Post(UrlString callUrl, OauthToken token, List<NameValuePair> postParam) {
        return callApiV2Post(callUrl, GetV2OauthHeader.getAuthorizationHeader(token), postParam, "application/json");
    }

    private String callApiV2Post(UrlString callUrl, String authHeader, List<NameValuePair> postParam, String contentType){
        String responseJsonStr;

        loggingStart(callUrl, HttpMethod.POST);
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost(callUrl.url);
            if(authHeader != null) {
                httpPost.addHeader("Authorization", authHeader);
            }
            httpPost.addHeader("Content-Type", contentType);
            if(postParam != null){
                httpPost.setEntity(new UrlEncodedFormEntity(postParam, StandardCharsets.UTF_8));
            }

            HttpEntity responseEntity = httpClient.execute(httpPost, response ->{
                if(response.getCode() != HttpStatus.SC_OK){
                    System.err.println("Response status code = "+ response.getCode() + response.getReasonPhrase());
                    throw new TwitterApiException("API response status code was not 200-OK");
                }
                return response.getEntity();
            });
            responseJsonStr = convertEntity2JsonStr(responseEntity);
        } catch (Exception e) {
            System.err.println("Exception occurred while calling Twitter API v2");
            System.err.println(e.getMessage());
            System.err.println(callUrl);
            throw new TwitterApiException(e);
        }

        System.out.println("==ApiResponse==>>>>>");
        System.out.println(responseJsonStr);
        System.out.println("==ApiResponse==<<<<<");

        loggingEnd(callUrl);
        return responseJsonStr;
    }


    private static void loggingStart(UrlString url, HttpMethod method){
        System.out.println("--- Api call start ----->");
        System.out.println("--- call URL = "+ url.url);
        System.out.println("--- call method = "+ method);
    }

    private static void loggingStart(URIBuilder url, HttpMethod method){
        System.out.println("--- Api call start ----->");
        System.out.println("--- call URL = "+ url.getPath());
        System.out.println("--- call method = "+ method);
    }

    private static void loggingEnd(UrlString url){
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
                jsonStr = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }
        } catch (IOException | ParseException e) {
            System.err.println("Exception occurred while converting API response data");
            System.err.println(e.getMessage());
            System.err.println(entity);
            throw new TwitterApiException(e);
        }

        return jsonStr;
    }
}
