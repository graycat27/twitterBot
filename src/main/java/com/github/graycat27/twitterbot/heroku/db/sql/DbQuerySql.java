package com.github.graycat27.twitterbot.heroku.db.sql;

import com.github.graycat27.twitterbot.heroku.db.Names;

public class DbQuerySql {

    private DbQuerySql(){ /* インスタンス化防止 */ }

    public static final SqlKey selectTodayString = new SqlKey(Names.Mybatis.dbQuery+".selectTodayString");
}
