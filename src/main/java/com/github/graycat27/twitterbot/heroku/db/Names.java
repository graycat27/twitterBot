package com.github.graycat27.twitterbot.heroku.db;

public class Names {

    public static class Mybatis {
        private static final String mybatisNameSpace = "twitterBot.";

        public static final String botUsers = mybatisNameSpace+"BotUserMapper";
        public static final String twitterAuth = mybatisNameSpace+"TwitterAuthMapper";
        public static final String twitterRecord = mybatisNameSpace+"TwitterRecordMapper";
    }

}
