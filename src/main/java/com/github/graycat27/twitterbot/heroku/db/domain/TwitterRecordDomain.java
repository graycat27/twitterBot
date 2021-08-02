package com.github.graycat27.twitterbot.heroku.db.domain;

import com.google.gson.Gson;

import java.sql.Timestamp;

public class TwitterRecordDomain extends CommonDomain {

    private final String twUserId;
    public String getTwUserId(){
        return twUserId;
    }
    private final Timestamp recordTime;
    public Timestamp getRecordTime(){
        return recordTime;
    }
    private final Integer totalTweetCount;
    public int getTotalTweetCount(){
        return totalTweetCount;
    }
    private final Timestamp dateRecordTime;
    public Timestamp getDateRecordTime(){
        return dateRecordTime;
    }
    private final Integer totalTweetCountAtDate;
    public int getTotalTweetCountAtDate(){
        return totalTweetCountAtDate;
    }
    private final String twDisplayId;
    public String getTwDisplayId(){
        return twDisplayId;
    }

    public TwitterRecordDomain(){
        this(null, null, null, null, null, null);
    }
    public TwitterRecordDomain(
        String twitterUserId,
        Timestamp recordTime,
        Integer totalTweetCount,
        Timestamp dateRecordTime,
        Integer totalTweetAtDate,
        String twitterDisplayId
    ){
        this.twUserId = twitterUserId;
        this.recordTime = recordTime;
        this.totalTweetCount = totalTweetCount;
        this.dateRecordTime = dateRecordTime;
        this.totalTweetCountAtDate = totalTweetAtDate;
        this.twDisplayId = twitterDisplayId;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
