package com.github.graycat27.twitterbot.heroku.db.sql;

import com.github.graycat27.twitterbot.heroku.db.Names;

public class BotUserSql {

    private BotUserSql(){ /* インスタンス化防止 */ }

    public static final SqlKey selectOne = new SqlKey(Names.Mybatis.botUsers + ".selectOne");
    public static final SqlKey selectMulti = new SqlKey(Names.Mybatis.botUsers + ".selectMulti");

    public static final SqlKey insert = new SqlKey(Names.Mybatis.botUsers + ".insert");
    public static final SqlKey update = new SqlKey(Names.Mybatis.botUsers + ".update");

    public static final SqlKey deleteLogical = new SqlKey(Names.Mybatis.botUsers + ".deleteLogical");
    public static final SqlKey restoreDeletedUser = new SqlKey(Names.Mybatis.botUsers + ".restoreDeletedUser");
    public static final SqlKey selectThoughDeleted = new SqlKey(Names.Mybatis.botUsers + ".selectThoughDeleted");

}
