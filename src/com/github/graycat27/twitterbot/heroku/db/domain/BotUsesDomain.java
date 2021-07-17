package com.github.graycat27.twitterbot.heroku.db.domain;

public class BotUsesDomain implements IDbDomain {

    public final String twUserId;

    public BotUsesDomain(
            String twUserId
    ){
        this.twUserId = twUserId;
    }

}
