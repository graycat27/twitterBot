package com.github.graycat27.twitterbot.heroku.db.sql;

import com.github.graycat27.twitterbot.heroku.db.Names;

public class TwitterAuthSql {

    private TwitterAuthSql(){ /* インスタンス化防止 */ }

    public static final String selectOne = Names.Mybatis.twitterAuth + ".selectOne";

}
