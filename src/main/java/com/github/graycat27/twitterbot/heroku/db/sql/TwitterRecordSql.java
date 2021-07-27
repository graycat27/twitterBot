package com.github.graycat27.twitterbot.heroku.db.sql;

import com.github.graycat27.twitterbot.heroku.db.Names;

public class TwitterRecordSql {

    public static final String selectOne = Names.Mybatis.twitterRecord + ".selectOne";
    public static final String selectMulti = Names.Mybatis.twitterRecord + ".selectMulti";

    public static final String insert = Names.Mybatis.twitterRecord + ".insert";
    public static final String update = Names.Mybatis.twitterRecord + ".update";
}
