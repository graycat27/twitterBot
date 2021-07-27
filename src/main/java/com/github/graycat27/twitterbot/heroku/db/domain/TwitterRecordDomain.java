package com.github.graycat27.twitterbot.heroku.db.domain;

import java.sql.Timestamp;

public class TwitterRecordDomain implements IDbDomain {

    private Timestamp recordTime;
    public Timestamp getRecordTime(){
        return recordTime;
    }
    private String twitterUserId;
    public String getTwitterUserId(){
        return twitterUserId;
    }
    private Integer totalTweetCount;
    public int getTotalTweetCount(){
        return totalTweetCount;
    }
    private String twitterDisplayId;
    public String getTwitterDisplayId(){
        return twitterDisplayId;
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
        this.twitterUserId = twitterUserId;
        this.totalTweetCount = totalTweetCount;
        this.twitterDisplayId = twitterDisplayId;
    }
}
