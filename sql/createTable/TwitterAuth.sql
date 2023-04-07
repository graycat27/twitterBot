CREATE TABLE TWITTER_AUTH (
    created_date timestamp DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp,
    is_deleted boolean DEFAULT false,
    api_key text,
    secret_key text,
    bearer_token text,
    client_id text,
    client_secret text
)