package com.github.graycat27.twitterbot.heroku.db.sql.define;

public class UpdateQuery implements ChangeQuery {

    private final String sqlQueryStr;

    public UpdateQuery(String sql){
        if(!sql.toLowerCase().startsWith("update ")){
            throw new IllegalArgumentException("query string not starts with UPDATE");
        }
        sqlQueryStr = sql;
    }


    @Override
    public String sql() {
        return sqlQueryStr;
    }
}
