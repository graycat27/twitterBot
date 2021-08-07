package com.github.graycat27.twitterbot.heroku.db.sql;

public class SqlKey {

    private final String sqlKey;

    public SqlKey(String key){
        this.sqlKey = key;
    }

    public String val(){
        return sqlKey;
    }

    @Override
    public String toString(){
        return "SqlKey:"+sqlKey;
    }
}
