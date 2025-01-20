package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.RedirectInfo;
import com.github.graycat27.twitterbot.utils.ListUtil;
import com.github.graycat27.twitterbot.utils.UrlString;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class RequestTokenGetterApi {

    private static final Logger logger = Logger.getLogger(RequestTokenGetterApi.class.getName());

    private RequestTokenGetterApi(){ /* インスタンス化防止 */ }

    public static RedirectInfo getRequestToken() throws URISyntaxException {

        URIBuilder uriBuilder = new URIBuilder(ApiUrl.getAuthorize.url);
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        TwitterAuthDomain authInfo = authQuery.selectOne(new TwitterAuthDomain());
        UrlString callBack = new UrlString("https://graycat27twitterbot.herokuapp.com/twitterAuthCallback");

        ArrayList<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("response_type", "code"));
        queryParameters.add(new BasicNameValuePair("client_id", authInfo.getClientId()));
        queryParameters.add(new BasicNameValuePair("redirect_uri", callBack.url));
        queryParameters.add(new BasicNameValuePair("scope", "tweet.read tweet.write users.read offline.access"));
        UUID state = UUID.randomUUID();
        queryParameters.add(new BasicNameValuePair("state", state.toString()));
        UUID challenge = UUID.randomUUID();
        queryParameters.add(new BasicNameValuePair("code_challenge", challenge.toString()));
        queryParameters.add(new BasicNameValuePair("code_challenge_method", "plain"));

        uriBuilder.addParameters(queryParameters);
        URI redirectUri = uriBuilder.build();
        UrlString authUrl = new UrlString(redirectUri);

        ListUtil.printList(queryParameters);
        logger.info(authUrl.url);

        return new RedirectInfo(state, challenge.toString(), authUrl);

    }

}
