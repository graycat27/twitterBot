package com.github.graycat27.twitterbot.heroku.db.sql;

import com.github.graycat27.twitterbot.heroku.db.Names;

public class TwitterRecordSql {

    private TwitterRecordSql(){ /* インスタンス化防止 */ }

    public static final String selectOne = Names.Mybatis.twitterRecord + ".selectOne";
    public static final String selectMulti = Names.Mybatis.twitterRecord + ".selectMulti";

    public static final String insert = Names.Mybatis.twitterRecord + ".insert";
    public static final String update = Names.Mybatis.twitterRecord + ".update";

    public static final String updateDaily = Names.Mybatis.twitterRecord + ".updateDaily";
}
