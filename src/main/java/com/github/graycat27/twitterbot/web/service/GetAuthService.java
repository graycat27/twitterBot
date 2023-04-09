package com.github.graycat27.twitterbot.web.service;

import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterUserTokenDomain;
import com.github.graycat27.twitterbot.heroku.db.query.BotUserQuery;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterUserTokenQuery;
import com.github.graycat27.twitterbot.twitter.api.caller.AccessTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.caller.RequestTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.caller.SendTweetApi;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.utils.TweetTemplate;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.UUID;

@Service
public class GetAuthService {

    public RequestTokenGetterApi.RedirectInfo getAuth(){
        try {
            return RequestTokenGetterApi.getRequestToken();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getUserAccessToken(String state, String code, UUID savedState, UUID savedChallenge){
        try{
            if(!savedState.toString().equals(state)){
                throw new IllegalStateException("token state is un matched");
            }
            return AccessTokenGetterApi.getAccessToken(code, savedChallenge);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * AccessToken からIDを取得し、DBのマスタ登録をする
     */
    public void registerUserAccessToken(AccessToken token){
        BotUserQuery userQuery = new BotUserQuery();
        TwitterUserTokenQuery tokenQuery = new TwitterUserTokenQuery();

        BotUsersDomain searchBotUser = new BotUsersDomain(token.getId());
        BotUsersDomain selectUserResult = userQuery.selectThoughDeleted(searchBotUser);
        /*
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
        */
        /* 登録したことをツイート */
        String status = TweetTemplate.authed;
        SendTweetApi.sendTweet(token.getId(), status);
    }

}
