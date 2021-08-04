package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterUserTokenDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterUserTokenQuery;
import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class SendTweetApi {

    private SendTweetApi(){ /* インスタンス化防止 */ }

    public static void sendTweet(String userId) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(ApiUrl.statusesUpdate.url);

        uriBuilder.addParameter("status", "#ツイート数えったー テストツイートです");

        RequestToken token = getTokenByUser(userId);
        ApiManager.getApiCaller().callApiV1Post(uriBuilder, token);

    }

    private static RequestToken getTokenByUser(String userId){
        TwitterUserTokenQuery tokenQuery = new TwitterUserTokenQuery();
        TwitterUserTokenDomain param = new TwitterUserTokenDomain(userId, null, null);

        TwitterUserTokenDomain resultDomain = tokenQuery.selectOne(param);
        return new RequestToken(userId, resultDomain.getOauthToken(), resultDomain.getOauthTokenSecret());
    }
}
