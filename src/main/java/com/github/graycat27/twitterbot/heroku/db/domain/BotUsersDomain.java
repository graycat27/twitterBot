package com.github.graycat27.twitterbot.heroku.db.domain;

import com.google.gson.Gson;

public class BotUsersDomain extends CommonDomain {

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
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
