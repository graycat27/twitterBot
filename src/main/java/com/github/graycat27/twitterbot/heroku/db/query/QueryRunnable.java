package com.github.graycat27.twitterbot.heroku.db.query;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public abstract class QueryRunnable implements IQuery {

    // フィールド
    protected SqlSessionFactory factory;

    // コンストラクタ
    public QueryRunnable(){
        try(InputStream in = QueryRunnable.class.getResourceAsStream("/mybatis/mybatis-config.xml")) {
            factory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
