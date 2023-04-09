package com.github.graycat27.twitterbot.web.service;

import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.query.BotUserQuery;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterUserTokenQuery;
import com.github.graycat27.twitterbot.twitter.api.caller.AccessTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.caller.GetUserInfoApi;
import com.github.graycat27.twitterbot.twitter.api.caller.RequestTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.caller.SendTweetApi;
import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.data.UserInfoData;
import com.github.graycat27.twitterbot.utils.TweetTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public void registerUserAccessToken(){

        try {
            ResponseCore<UserInfoData> res = GetUserInfoApi.getUser("me");
            UserInfoData userInfo = res.getData();

            BotUserQuery userQuery = new BotUserQuery();
            TwitterUserTokenQuery tokenQuery = new TwitterUserTokenQuery();

            BotUsersDomain searchBotUser = new BotUsersDomain((String) userInfo.get("id"));
            BotUsersDomain selectUserResult = userQuery.selectThoughDeleted(searchBotUser);
/*
            if(selectUserResult == null){
                userQuery.insert(searchBotUser);
            }else{
                userQuery.restoreDeletedUser(searchBotUser);
                TwitterUserTokenDomain deleteDomain = new TwitterUserTokenDomain(
                        (String) userInfo.get("id"), null, null
                );
                tokenQuery.delete(deleteDomain);
            }

            TwitterUserTokenDomain insertDomain = new TwitterUserTokenDomain(
                    (String)userInfo.get("id"), token.getToken(), token.getTokenSecret()
            );
            tokenQuery.insert(insertDomain);
*/
            /* 登録したことをツイート */
            String status = TweetTemplate.authed;
            SendTweetApi.sendTweet((String)userInfo.get("id"), status);

        } catch (URISyntaxException|IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
