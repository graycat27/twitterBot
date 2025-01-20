package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.data.UserInfoData;
import com.github.graycat27.twitterbot.utils.JsonUtil;
import com.github.graycat27.twitterbot.utils.exception.TwitterApiException;
import com.google.gson.reflect.TypeToken;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GetUserInfoApi {

    private GetUserInfoApi(){ /* インスタンス化防止 */ }

    /**
     * https://github.com/twitterdev/Twitter-API-v2-sample-code/blob/main/User-Lookup/UsersDemo.java
     */
    public static ResponseCore<UserInfoData> getUser(String id){
        try {
            URIBuilder uriBuilder = new URIBuilder(ApiUrl.userById + id);

            ArrayList<NameValuePair> queryParameters = new ArrayList<>();
            queryParameters.add(new BasicNameValuePair("user.fields", "id,name,username,public_metrics"));
            uriBuilder.addParameters(queryParameters);

            String resJson = ApiManager.getApiCaller().callApiV2Get(ApiUrl.userById, uriBuilder);

            Type dataType = new TypeToken<ResponseCore<UserInfoData>>() {
            }.getType();
            ResponseCore<UserInfoData> data = JsonUtil.getObjectFromJsonStr(resJson, dataType);

            return data;
        }catch(URISyntaxException e){
            throw new TwitterApiException(e);
        }
    }
}
