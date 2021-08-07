package com.github.graycat27.twitterbot.heroku.db.sql;

import com.github.graycat27.twitterbot.heroku.db.Names;

public class TwitterUserTokenSql {

    private TwitterUserTokenSql(){ /* インスタンス化防止 */ }

    public static final SqlKey selectOne = new SqlKey(Names.Mybatis.twitterUserToken + ".selectOne");
    public static final SqlKey selectMulti = new SqlKey(Names.Mybatis.twitterUserToken + ".selectMulti");

    public static final SqlKey insert = new SqlKey(Names.Mybatis.twitterUserToken + ".insert");
    public static final SqlKey update = new SqlKey(Names.Mybatis.twitterUserToken + ".update");

    public static final SqlKey delete = new SqlKey(Names.Mybatis.twitterUserToken + ".delete");
}
