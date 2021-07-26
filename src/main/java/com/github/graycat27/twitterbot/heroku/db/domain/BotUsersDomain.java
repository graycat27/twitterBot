package com.github.graycat27.twitterbot.heroku.db.domain;

public class BotUsersDomain implements IDbDomain {

    private String twUserId;
    public String getTwUserId(){
        return twUserId;
    }

    public BotUsersDomain(){
        this(null);
    }
    public BotUsersDomain(String twUserId){
        this.twUserId = twUserId;
    }

    @Override
    public String toString() {
        return "BotUsersDomain:{twUserId:"+getTwUserId()+"}";
    }
}
