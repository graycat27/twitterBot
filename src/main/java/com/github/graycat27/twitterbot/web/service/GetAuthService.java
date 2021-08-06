package com.github.graycat27.twitterbot.web.service;

import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterUserTokenDomain;
import com.github.graycat27.twitterbot.heroku.db.query.BotUserQuery;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterUserTokenQuery;
import com.github.graycat27.twitterbot.twitter.api.caller.AccessTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.caller.RequestTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.caller.SendTweetApi;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;

@Service
public class GetAuthService {

    public RequestToken getAuth(){
        RequestToken apiResult;
        try {
            apiResult = RequestTokenGetterApi.getRequestToken();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return apiResult;
    }

    public AccessToken getUserAccessToken(String token, String verifier){

        AccessToken apiResult;
        try{
            apiResult = AccessTokenGetterApi.getAccessToken(token, verifier);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return apiResult;
    }

    /**
     * AccessToken からIDを取得し、DBのマスタ登録をする
     */
    public void registerUserAccessToken(AccessToken token){
        BotUserQuery userQuery = new BotUserQuery();
        TwitterUserTokenQuery tokenQuery = new TwitterUserTokenQuery();

        BotUsersDomain searchBotUser = new BotUsersDomain(token.getId());
        BotUsersDomain selectUserResult = userQuery.selectThoughDeleted(searchBotUser);
        if(selectUserResult == null){
            userQuery.insert(searchBotUser);
        }else{
            userQuery.restoreDeletedUser(searchBotUser);
            TwitterUserTokenDomain deleteDomain = new TwitterUserTokenDomain(
                    token.getId(), null, null
            );
            tokenQuery.delete(deleteDomain);
        }

        TwitterUserTokenDomain insertDomain = new TwitterUserTokenDomain(
            token.getId(), token.getToken(), token.getTokenSecret()
        );
        tokenQuery.insert(insertDomain);


        /*  */
        try {
            String dateTime = Calendar.getInstance().getTime().toString();
            String status = "#ツイート数えったー テストツイートです "+ dateTime;
            SendTweetApi.sendTweet(token.getId(), status);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

}
