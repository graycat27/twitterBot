package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.DBConnection;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.sql.TwitterAuthSql;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TwitterAuthQuery extends QueryRunnable {
    @Override
    public void insert(IDbDomain param) {
        throw new UnsupportedOperationException("this table is unable to insert");
    }

    @Override
    public void update(IDbDomain cond, IDbDomain param) {
        throw new UnsupportedOperationException("this table is unable to update");
    }

    @Override
    public TwitterAuthDomain selectOne(IDbDomain param) {
        if(param != null && !(param instanceof TwitterAuthDomain)){
            throw new IllegalArgumentException("param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            TwitterAuthDomain domainParam = (TwitterAuthDomain) param;
            logParamObject(domainParam);
            TwitterAuthDomain result = session.selectOne(TwitterAuthSql.selectOne, domainParam);
            logResultObjectSecret(result);
            return result;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public List<TwitterAuthDomain> selectMulti(IDbDomain param) {
        throw new UnsupportedOperationException("this table is unable to select multi");
    }
}
