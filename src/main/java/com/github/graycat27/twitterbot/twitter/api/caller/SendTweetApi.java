package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterUserTokenDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterUserTokenQuery;
import com.github.graycat27.twitterbot.twitter.api.ApiManager;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class SendTweetApi {

    private SendTweetApi(){ /* インスタンス化防止 */ }

    public static void sendTweet(String userId, String tweetText) {

        List<NameValuePair> postParam = new ArrayList<>();
        postParam.add(new BasicNameValuePair("text", tweetText));

        AccessToken token = getTokenByUser(userId);
        ApiManager.getApiCaller().callApiV2Post(ApiUrl.postTweet, token, postParam);

    }

    private static AccessToken getTokenByUser(String userId){
        TwitterUserTokenQuery tokenQuery = new TwitterUserTokenQuery();
        TwitterUserTokenDomain param = new TwitterUserTokenDomain(userId, null, null);

        TwitterUserTokenDomain resultDomain = tokenQuery.selectOne(param);
        return new AccessToken(resultDomain.getOauthToken(), resultDomain.getOauthTokenSecret(),
                resultDomain.getTwUserId(), null);
    }
}
