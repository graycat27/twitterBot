package com.github.graycat27.twitterbot.heroku.db.sql.define;

public class InsertQuery implements ChangeQuery {

    private final String sqlQueryStr;

    public InsertQuery(String sql){
        if(!sql.toLowerCase().startsWith("insert into ")){
            throw new IllegalArgumentException("query string not starts with INSERT INTO");
        }
        sqlQueryStr = sql;
    }


    @Override
    public String sql() {
        return sqlQueryStr;
    }
}
