package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.DBConnection;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterUserTokenDomain;
import com.github.graycat27.twitterbot.heroku.db.sql.SqlKey;
import com.github.graycat27.twitterbot.heroku.db.sql.TwitterUserTokenSql;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

public class TwitterUserTokenQuery extends QueryRunnable {
    @Override
    public void insert(IDbDomain param) {
        if(param != null && !(param instanceof TwitterUserTokenDomain)){
            throw new IllegalArgumentException("param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            SqlKey sql = TwitterUserTokenSql.insert;
            TwitterUserTokenDomain domainParam = (TwitterUserTokenDomain) param;
            logParamObjectSecret(sql, domainParam);
            int result = session.insert(sql.val(), domainParam);
            logResultObject(sql, result);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public void update(IDbDomain cond, IDbDomain param) {
        //use delete-insert
        throw new UnsupportedOperationException("this table is unable to update");
    }

    @Override
    public TwitterUserTokenDomain selectOne(IDbDomain param) {
        if(param != null && !(param instanceof TwitterUserTokenDomain)){
            throw new IllegalArgumentException("param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            SqlKey sql = TwitterUserTokenSql.selectOne;
            TwitterUserTokenDomain domainParam = (TwitterUserTokenDomain) param;
            logParamObject(sql, param);
            TwitterUserTokenDomain result = session.selectOne(sql.val(), domainParam);
            //logResultObjectSecret(sql, result);
            logResultObject(sql, result); //for debug
            return result;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public List<TwitterUserTokenDomain> selectMulti(IDbDomain param) {
        throw new UnsupportedOperationException("this table is unable to select multi");
    }

    public void delete(TwitterUserTokenDomain param){
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            SqlKey sql = TwitterUserTokenSql.delete;
            logParamObject(sql, param);
            int result = session.delete(sql.val(), param);
            logResultObject(sql, result);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }
}
