package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.MybatisConfig;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.sql.SqlKey;
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

    protected void logParamObject(final SqlKey sql, final IDbDomain domain){
        if(domain == null){
            System.out.println("== DB-I/O parameter for "+ sql.val() +" is nothing ==>");
            return;
        }
        System.out.println("== DB-I/O parameter == "+ sql.val() +" ==>");
        System.out.println(domain);
        System.out.println("====<");
    }

    protected void logParamObjectSecret(final SqlKey sql, final IDbDomain domain){
        if(domain == null){
            System.out.println("== DB-I/O parameter for "+ sql.val() +" is nothing ==>");
            return;
        }
        System.out.println("== DB-I/O parameter == "+ sql.val() +" ==>");
        System.out.println(domain.getClass().getSimpleName() + "**data-secret**");
        System.out.println("====<");
    }

    protected void logResultObject(final SqlKey sql, final IDbDomain domain){
        System.out.println("== DB-I/O result == "+ sql.val() +" ==>");
        System.out.println(domain.toString());
        System.out.println("====<");
    }

    protected void logResultObject(final SqlKey sql, final int count){
        System.out.println("== DB-I/O result == "+ sql.val() +" ==>");
        System.out.println(count);
        System.out.println("====<");
    }

    protected void logResultObject(final SqlKey sql, final List<? extends IDbDomain> resultList){
        System.out.println("== DB-I/O result == "+ sql.val() +" ==>");
        ListUtil.printList(resultList);
        System.out.println("====<");
    }

    protected void logResultObjectSecret(final SqlKey sql, final IDbDomain domain){
        System.out.println("== DB-I/O result == "+ sql.val() +" ==>");
        System.out.println(domain.getClass().getSimpleName() + "**data-secret**");
        System.out.println("====<");
    }

    protected void logResultObjectSecret(final SqlKey sql, final List<? extends IDbDomain> resultList){
        System.out.println("== DB-I/O result == "+ sql.val() +" ==>");
        ListUtil.printSecretDataList(resultList);
        System.out.println("====<");
    }

}
