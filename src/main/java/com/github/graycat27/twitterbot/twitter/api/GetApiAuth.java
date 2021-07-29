package com.github.graycat27.twitterbot.twitter.api;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;

public class GetApiAuth {

    public static TwitterAuthDomain getTwitterAuth() {
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        return authQuery.selectOne(null);
    }
}
