CREATE TABLE TWITTER_RECORD (
    created_date timestamp DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp,
    is_deleted boolean DEFAULT false,
    tw_user_id text PRIMARY KEY REFERENCES bot_users (tw_user_id),
    record_time timestamp,
    total_tweet_count integer,
    date_record_time timestamp,
    total_tweet_count_at_date integer,
    tw_display_id text
)