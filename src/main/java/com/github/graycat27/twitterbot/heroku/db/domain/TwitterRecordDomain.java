package com.github.graycat27.twitterbot.heroku.db.domain;

import com.google.gson.Gson;

import java.sql.Timestamp;

public class TwitterRecordDomain extends CommonDomain {

    private Timestamp recordTime;
    public Timestamp getRecordTime(){
        return recordTime;
    }
    private String twUserId;
    public String getTwUserId(){
        return twUserId;
    }
    private Integer totalTweetCount;
    public int getTotalTweetCount(){
        return totalTweetCount;
    }
    private String twDisplayId;
    public String getTwDisplayId(){
        return twDisplayId;
    }

    public TwitterRecordDomain(){
        this(null, null, null, null);
    }
    public TwitterRecordDomain(
        Timestamp recordTime,
        String twitterUserId,
        Integer totalTweetCount,
        String twitterDisplayId
    ){
        this.recordTime = recordTime;
        this.twUserId = twitterUserId;
        this.totalTweetCount = totalTweetCount;
        this.twDisplayId = twitterDisplayId;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
