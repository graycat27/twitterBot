package com.github.graycat27.twitterbot.heroku.db.sql;

import com.github.graycat27.twitterbot.heroku.db.Names;

public class TwitterRecordSql {

    private TwitterRecordSql(){ /* インスタンス化防止 */ }

    public static final SqlKey selectOne = new SqlKey(Names.Mybatis.twitterRecord + ".selectOne");
    public static final SqlKey selectMulti = new SqlKey(Names.Mybatis.twitterRecord + ".selectMulti");

    public static final SqlKey insert = new SqlKey(Names.Mybatis.twitterRecord + ".insert");
    public static final SqlKey update = new SqlKey(Names.Mybatis.twitterRecord + ".update");

    public static final SqlKey updateDaily = new SqlKey(Names.Mybatis.twitterRecord + ".updateDaily");
}
