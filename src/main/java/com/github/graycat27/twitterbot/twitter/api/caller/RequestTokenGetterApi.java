package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import com.github.graycat27.twitterbot.utils.UrlString;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.List;

public class RequestTokenGetterApi {

    private RequestTokenGetterApi(){ /* インスタンス化防止 */ }

    /*
     * ref: https://developer.twitter.com/ja/docs/authentication/oauth-1-0a/authorizing-a-request
     * ref: https://n3104.hatenablog.com/entry/20101014/1287070373
     */
    public static RequestToken getRequestToken() throws URISyntaxException {

        String resStr = ApiManager.getApiCaller().callApiV1Post(ApiUrl.getRequestToken, null, null);

        return convertQueryStr2Domain(resStr);
    }

    private static RequestToken convertQueryStr2Domain(String queryStr) throws URISyntaxException {
        try {
            URIBuilder urlBuilder = new URIBuilder("?"+ queryStr);
            List<NameValuePair> params = urlBuilder.getQueryParams();
            String token = null;
            String tokenSecret = null;
            for (NameValuePair keyVal : params) {
                switch (keyVal.getName()) {
                    case "oauth_token":
                        token = keyVal.getValue();
                        break;
                    case "oauth_token_secret":
                        tokenSecret = keyVal.getValue();
                        break;
                }
            }
            return new RequestToken(token, tokenSecret, null);
        }catch(URISyntaxException e){
            System.err.println("*** error while converting request param to domain ***");
            System.err.println("*** tried to convert = "+ queryStr);
            throw e;
        }
    }

}
