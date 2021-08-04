package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.DBConnection;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterUserTokenDomain;
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
            TwitterUserTokenDomain domainParam = (TwitterUserTokenDomain) param;
            session.insert(TwitterUserTokenSql.insert, domainParam);
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
            TwitterUserTokenDomain domainParam = (TwitterUserTokenDomain) param;
            return session.selectOne(TwitterUserTokenSql.selectOne, domainParam);
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
            session.delete(TwitterUserTokenSql.delete, param);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }
}