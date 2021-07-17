package com.github.graycat27.twitterbot.heroku.db.domain;

import java.sql.Timestamp;

public class TwitterRecordDomain {

    public final Timestamp recordTime;
    public final String twitterUserId;
    public final int totalTweetCount;
    public final String twitterDisplayId;

    public TwitterRecordDomain(
        Timestamp recordTime,
        String twitterUserId,
        int totalTweetCount,
        String twitterDisplayId
    ){
        this.recordTime = recordTime;
        this.twitterUserId = twitterUserId;
        this.totalTweetCount = totalTweetCount;
        this.twitterDisplayId = twitterDisplayId;
    }
}
