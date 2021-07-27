CREATE TABLE BOT_USERS (
    created_date timestamp DEFAULT CURRENT_TIMESTAMP,
    update_date timestamp,
    is_deleted boolean DEFAULT false,
    tw_user_id text PRIMARY KEY
)