package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.utils.ListUtil;
import com.github.graycat27.twitterbot.utils.UrlString;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class RequestTokenGetterApi {

    private static final Logger logger = Logger.getLogger(RequestTokenGetterApi.class.getName());

    private RequestTokenGetterApi(){ /* インスタンス化防止 */ }

    public static class RedirectInfo{
        public final UUID state;
        public final UrlString url;
        /*  */RedirectInfo(UUID state, UrlString url){
            this.state = state; this.url = url;
        }
    }

    public static RedirectInfo getRequestToken() throws URISyntaxException {

        URIBuilder uriBuilder = new URIBuilder(ApiUrl.getAuthorize.url);

        ArrayList<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("response_type", "code"));
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        TwitterAuthDomain authInfo = authQuery.selectOne(new TwitterAuthDomain());
        queryParameters.add(new BasicNameValuePair("client_id", authInfo.getClientId()));
        UrlString callBack = new UrlString("https://graycat27twitterbot.herokuapp.com/twitterAuthCallback");
        queryParameters.add(new BasicNameValuePair("redirect_uri", callBack.url));
        String scope = "tweet.read tweet.write users.read offline.access";
        queryParameters.add(new BasicNameValuePair("scope", scope));
        UUID uid = UUID.randomUUID();
        queryParameters.add(new BasicNameValuePair("state", uid.toString()));
        queryParameters.add(new BasicNameValuePair("code_challenge", "challenge"));
        queryParameters.add(new BasicNameValuePair("code_challenge_method", "plain"));

        uriBuilder.addParameters(queryParameters);
        URI redirectUri = uriBuilder.build();
        UrlString authUrl = new UrlString(redirectUri);

        ListUtil.printList(queryParameters);
        logger.info(authUrl.url);

        return new RedirectInfo(uid, authUrl);

    }

}
