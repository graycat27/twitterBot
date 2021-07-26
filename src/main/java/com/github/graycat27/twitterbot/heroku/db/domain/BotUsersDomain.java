package com.github.graycat27.twitterbot.heroku.db.domain;

public class BotUsersDomain implements IDbDomain {

    public final String twUserId;

    public BotUsersDomain(
            String twUserId
    ){
        this.twUserId = twUserId;
    }

}
