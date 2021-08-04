package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class AccessTokenGetterApi {

    private AccessTokenGetterApi(){ /* インスタンス化防止 */ }

    public static AccessToken getAccessToken(String token, String verifier) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(ApiUrl.getAccessToken.url);
        RequestToken requestToken = new RequestToken(token, null, verifier);
        String resStr = ApiManager.getApiCaller().callApiV1Post(uriBuilder, requestToken, null);

        AccessToken data = convertQueryStr2Domain(resStr);

        System.out.println("============>>>>>");
        System.out.println(data);
        System.out.println("============<<<<<");

        return data;
    }

    private static AccessToken convertQueryStr2Domain(String queryStr) throws URISyntaxException{
        try {
            URIBuilder urlBuilder = new URIBuilder("?"+ queryStr);
            List<NameValuePair> params = urlBuilder.getQueryParams();
            String token = null;
            String tokenSecret = null;
            String userId = null;
            String screenName = null;
            for (NameValuePair keyVal : params) {
                switch (keyVal.getName()) {
                    case "oauth_token":
                        token = keyVal.getValue();
                        break;
                    case "oauth_token_secret":
                        tokenSecret = keyVal.getValue();
                        break;
                    case "user_id":
                        userId = keyVal.getValue();
                        break;
                    case "screen_name":
                        screenName = keyVal.getValue();
                        break;
                }
            }
            return new AccessToken(token, tokenSecret, userId, screenName);
        }catch(URISyntaxException e){
            System.err.println("*** error while converting request param to domain ***");
            System.err.println("*** tried to convert = "+ queryStr);
            throw e;
        }
    }
}
