package com.github.graycat27.twitterbot.web.service;

import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterUserTokenDomain;
import com.github.graycat27.twitterbot.heroku.db.query.BotUserQuery;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterUserTokenQuery;
import com.github.graycat27.twitterbot.twitter.api.RedirectInfo;
import com.github.graycat27.twitterbot.twitter.api.caller.AccessTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.caller.GetUserInfoApi;
import com.github.graycat27.twitterbot.twitter.api.caller.RequestTokenGetterApi;
import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.twitter.api.response.data.UserInfoData;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.UUID;

@Service
public class GetAuthService {

    public RedirectInfo getAuth(){
        try {
            return RequestTokenGetterApi.getRequestToken();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public AccessToken getUserRefreshToken(String state, String code, UUID savedState, String savedChallenge){
        try{
            if(!savedState.toString().equals(state)){
                throw new IllegalStateException("token state is un matched");
            }
            return AccessTokenGetterApi.getAccessToken(code, savedChallenge);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AccessToken からIDを取得し、DBのマスタ登録をする
     */
    public void registerUserAccessToken(AccessToken token){

        TwitterUserTokenQuery tokenQuery = new TwitterUserTokenQuery();
        BotUserQuery userQuery = new BotUserQuery();

        ResponseCore<UserInfoData> res = GetUserInfoApi.getUser("me", token);
        token.setId(res.getData().getId());

        BotUsersDomain searchBotUser = new BotUsersDomain(token.getId());
        BotUsersDomain selectUserResult = userQuery.selectThoughDeleted(searchBotUser);

        //ユーザデータを有効化
        if(selectUserResult == null){
            userQuery.insert(searchBotUser);
        }else{
            userQuery.restoreDeletedUser(searchBotUser);
            TwitterUserTokenDomain deleteDomain = new TwitterUserTokenDomain(
                    token.getId(), null, null, null
            );
            tokenQuery.delete(deleteDomain);
        }

        //トークン情報を最新化
        TwitterUserTokenDomain insertDomain = new TwitterUserTokenDomain(
                token.getId(), token.getToken(), token.getTokenSecret(), token.getRefreshToken()
        );
        tokenQuery.insert(insertDomain);

    }

}
