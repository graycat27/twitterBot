package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.utils.ListUtil;
import com.github.graycat27.twitterbot.utils.UrlString;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class AccessTokenGetterApi {

    private AccessTokenGetterApi(){ /* インスタンス化防止 */ }

    public static void getAccessToken(String code) throws URISyntaxException {

        ArrayList<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("code", code));
        queryParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        TwitterAuthDomain authInfo = authQuery.selectOne(new TwitterAuthDomain());
        queryParameters.add(new BasicNameValuePair("client_id", authInfo.getClientId()));
        UrlString callback = new UrlString("https://graycat27twitterbot.herokuapp.com/twitterAuthComplete");
        queryParameters.add(new BasicNameValuePair("redirect_uri", callback.url));
        queryParameters.add(new BasicNameValuePair("code_verifier", "challenge"));

        ListUtil.printList(queryParameters);

        ApiManager.getApiCaller().callApiV2PostUrlEncodedContent(ApiUrl.getAccessToken, queryParameters);

    }

}
