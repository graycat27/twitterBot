CREATE TABLE twitter_user_token (
    created_date timestamp DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp,
    is_deleted boolean DEFAULT false,
    tw_user_id text PRIMARY KEY REFERENCES bot_users (tw_user_id),
    oauth_token text,
    oauth_token_secret text
);

ALTER TABLE twitter_user_token
ADD COLUMN oauth_refresh_token text;