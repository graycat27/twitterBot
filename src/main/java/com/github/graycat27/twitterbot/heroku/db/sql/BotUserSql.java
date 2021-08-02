package com.github.graycat27.twitterbot.heroku.db.sql;

import com.github.graycat27.twitterbot.heroku.db.Names;

public class BotUserSql {

    private BotUserSql(){ /* インスタンス化防止 */ }

    public static final String selectOne = Names.Mybatis.botUsers + ".selectOne";
    public static final String selectMulti = Names.Mybatis.botUsers + ".selectMulti";

    public static final String insert = Names.Mybatis.botUsers + ".insert";
    public static final String update = Names.Mybatis.botUsers + ".update";

}
