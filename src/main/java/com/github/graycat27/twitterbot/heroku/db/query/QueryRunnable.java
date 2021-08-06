package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.MybatisConfig;
import org.apache.ibatis.session.SqlSessionFactory;

public abstract class QueryRunnable implements IQuery {

    // フィールド
    protected SqlSessionFactory factory;

    // コンストラクタ
    public QueryRunnable(){
        try {
            factory = MybatisConfig.sqlSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
