package com.github.graycat27.twitterbot.heroku.db.sql;

import com.github.graycat27.twitterbot.heroku.db.Names;

public class BotUserSql {

    public static final String selectOne = Names.Mybatis.botUsers + ".selectOne";
    public static final String selectAll = Names.Mybatis.botUsers + ".selectMulti";

    public static final String insert = Names.Mybatis.botUsers + ".insert";
    public static final String update = Names.Mybatis.botUsers + ".update";

}
