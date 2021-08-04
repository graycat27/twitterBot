package com.github.graycat27.twitterbot.heroku.db.sql;

import com.github.graycat27.twitterbot.heroku.db.Names;

public class TwitterUserTokenSql {

    private TwitterUserTokenSql(){ /* インスタンス化防止 */ }

    public static final String selectOne = Names.Mybatis.twitterUserToken + ".selectOne";
    public static final String selectMulti = Names.Mybatis.twitterUserToken + ".selectMulti";

    public static final String insert = Names.Mybatis.twitterUserToken + ".insert";
    public static final String update = Names.Mybatis.twitterUserToken + ".update";

    public static final String delete = Names.Mybatis.twitterUserToken + ".delete";
}
