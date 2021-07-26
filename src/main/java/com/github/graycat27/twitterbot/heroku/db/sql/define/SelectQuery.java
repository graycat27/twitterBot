package com.github.graycat27.twitterbot.heroku.db.sql.define;

public class SelectQuery implements SqlQuery {

    private final String sqlQueryStr;

    public SelectQuery(String sql){
        if(!sql.toLowerCase().startsWith("select ")){
            throw new IllegalArgumentException("query string not starts with SELECT");
        }
        sqlQueryStr = sql;
    }

    @Override
    public String sql() {
        return sqlQueryStr;
    }
}
