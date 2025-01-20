package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.utils.JsonUtil;
import com.github.graycat27.twitterbot.utils.ListUtil;
import com.github.graycat27.twitterbot.utils.UrlString;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

public class AccessTokenGetterApi {

    private AccessTokenGetterApi(){ /* インスタンス化防止 */ }

    public static AccessToken getAccessToken(String code, String challenge) throws URISyntaxException {

        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        TwitterAuthDomain authInfo = authQuery.selectOne(new TwitterAuthDomain());
        UrlString callback = new UrlString("https://graycat27twitterbot.herokuapp.com/twitterAuthComplete");

        ArrayList<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("code", code));
        queryParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        queryParameters.add(new BasicNameValuePair("client_id", authInfo.getClientId()));
        queryParameters.add(new BasicNameValuePair("redirect_uri", callback.url));
        queryParameters.add(new BasicNameValuePair("code_verifier", challenge));

        ListUtil.printList(queryParameters);

        String response = ApiManager.getApiCaller().callApiV2PostUrlEncodedContent(ApiUrl.getRefreshToken, queryParameters);
        System.out.println(response);
        return JsonUtil.getObjectFromJsonStr(response, AccessToken.class);

    }

}
