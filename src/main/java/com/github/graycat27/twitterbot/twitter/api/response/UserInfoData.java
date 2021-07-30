package com.github.graycat27.twitterbot.twitter.api.response;

public class UserInfoData extends IMetaData {
    private String id;
    private String username;
    private PublicMetrics public_metrics;
    private String name;
    private String pinned_tweet_id;
    private String description;

    public class PublicMetrics extends IMetaData{
        private int Followers_count;
        private int following_count;
        private int tweet_count;
        private int listed_count;
    }
}


