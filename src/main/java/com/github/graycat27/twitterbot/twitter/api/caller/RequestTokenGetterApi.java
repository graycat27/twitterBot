package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.utils.UrlString;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

public class RequestTokenGetterApi {

    private RequestTokenGetterApi(){ /* インスタンス化防止 */ }

    /*
     * ref: https://developer.twitter.com/ja/docs/authentication/oauth-1-0a/authorizing-a-request
     * ref: https://n3104.hatenablog.com/entry/20101014/1287070373
     */
    public static String getRequestToken() throws URISyntaxException {

        URIBuilder uriBuilder = new URIBuilder(ApiUrl.getAuthorize.url);

        ArrayList<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("response_type", "code"));
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        TwitterAuthDomain authInfo = authQuery.selectOne(new TwitterAuthDomain());
        queryParameters.add(new BasicNameValuePair("client_id", authInfo.getClientId()));
        UrlString callBack = new UrlString("https://graycat27twitterbot.herokuapp.com/twitterAuthCallback");
        queryParameters.add(new BasicNameValuePair("redirect_uri", callBack.url));
        String scope = "tweet.read%20tweet.write%20users.read%20offline.access";
        queryParameters.add(new BasicNameValuePair("scope", scope));
        UUID uid = UUID.randomUUID();
        queryParameters.add(new BasicNameValuePair("state", uid.toString()));
        queryParameters.add(new BasicNameValuePair("code_challenge", "challenge"));
        queryParameters.add(new BasicNameValuePair("code_challenge_method", "plain"));

        uriBuilder.addParameters(queryParameters);
        ApiManager.getApiCaller().callApiV2Get(uriBuilder);
        //twitter will redirect to redirect-uri

        return uid.toString();

    }

}
