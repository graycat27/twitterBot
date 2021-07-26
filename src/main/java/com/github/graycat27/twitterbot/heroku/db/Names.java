package com.github.graycat27.twitterbot.heroku.db;

public class Names {



    public static class Mybatis {
        private static final String mybatisNameSpace = "twitterBot.";
        public static final String botUsers = mybatisNameSpace+"BotUserMapper";
        public static final String twitterAuth = mybatisNameSpace+"TwitterAuth";
        public static final String twitterRecord = mybatisNameSpace+"TwitterRecord";
    }

    public static class Table {
        public static final String TWITTER_RECORD = "twitter_record";
        public static final String TWITTER_AUTH = "twitter_auth";
        public static final String BOT_USERS = "bot_users";
    }

    public static class Column {
        public static class TwRc {
            public static final String RECORD_TIME = "record_time";
            public static final String TWITTER_USER_ID = "tw_user_id";
            public static final String TOTAL_TW_CNT = "total_tweet_count";
            public static final String TWITTER_DISPLAY_ID = "tw_display_id";
        }

        public static class TwAuth {
            public static final String API_KEY = "apikey";
            public static final String SECRET_KEY = "secret_key";
            public static final String TOKEN = "bearer_token";
        }

        public static class BotUsers {
            public static final String TWITTER_USER_ID = "tw_user_id";
        }
    }

}
