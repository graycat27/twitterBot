CREATE TABLE TWITTER_AUTH (
    created_date timestamp DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp,
    is_deleted boolean DEFAULT false,
    apikey text,
    secret_key text,
    bearer_token text
)