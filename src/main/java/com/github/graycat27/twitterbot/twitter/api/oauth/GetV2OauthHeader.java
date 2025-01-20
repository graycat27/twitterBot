package com.github.graycat27.twitterbot.twitter.api.oauth;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.twitter.api.response.data.OauthToken;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GetV2OauthHeader {

    private GetV2OauthHeader(){
        /* インスタンス化防止 */
    }

    public static String getAuthorizationHeader(AccessToken token){
        String res = "Bearer "+token.getToken();
        System.out.println(res);
        return res;
    }

    public static String getAuthorizationHeader(){
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        TwitterAuthDomain record = authQuery.selectOne(null);
        String base = record.getClientId()+":"+record.getClientSecret();
        String res = "Basic "+Base64.getEncoder().encodeToString(base.getBytes(StandardCharsets.UTF_8));
        System.out.println(res);
        return res;
    }
}
