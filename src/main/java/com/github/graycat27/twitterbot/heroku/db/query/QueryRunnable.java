package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.MybatisConfig;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.utils.ListUtil;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

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

    protected void logParamObject(IDbDomain domain){
        if(domain == null){
            System.out.println("== DB-I/O parameter is nothing ==>");
            return;
        }
        System.out.println("== DB-I/O parameter ==>");
        System.out.println(domain.toString());
        System.out.println("====<");
    }

    protected void logResultObject(IDbDomain domain){
        System.out.println("== DB-I/O result ==>");
        System.out.println(domain.toString());
        System.out.println("====<");
    }

    protected void logResultObject(int count){
        System.out.println("== DB-I/O result ==>");
        System.out.println(count);
        System.out.println("====<");
    }

    protected void logResultObject(List<? extends IDbDomain> resultList){
        System.out.println("== DB-I/O result ==>");
        ListUtil.printList(resultList);
        System.out.println("====<");
    }

}
