package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterUserTokenDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterUserTokenQuery;
import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SendTweetApi {

    private SendTweetApi(){ /* インスタンス化防止 */ }

    public static void sendTweet(String userId, String tweetText) throws URISyntaxException, IOException {

        List<NameValuePair> postParam = new ArrayList<>();
        postParam.add(new BasicNameValuePair("status", tweetText));

        AccessToken token = getTokenByUser(userId);
        ApiManager.getApiCaller().callApiV1Post(ApiUrl.statusesUpdate, token, postParam);

    }

    private static AccessToken getTokenByUser(String userId){
        TwitterUserTokenQuery tokenQuery = new TwitterUserTokenQuery();
        TwitterUserTokenDomain param = new TwitterUserTokenDomain(userId, null, null);

        TwitterUserTokenDomain resultDomain = tokenQuery.selectOne(param);
        return new AccessToken(resultDomain.getOauthToken(), resultDomain.getOauthTokenSecret(),
                resultDomain.getTwUserId(), null);
    }
}
