package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.DBConnection;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import com.github.graycat27.twitterbot.heroku.db.sql.BotUserSql;
import com.github.graycat27.twitterbot.heroku.db.sql.TwitterRecordSql;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

public class TwitterRecordQuery extends QueryRunnable{
    @Override
    public void insert(IDbDomain param) {
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            session.insert(TwitterRecordSql.insert, param);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public void update(IDbDomain cond, IDbDomain param) {
        if(!(cond instanceof TwitterRecordDomain) || !(param instanceof TwitterRecordDomain)){
            throw new IllegalArgumentException("cond or param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            TwitterRecordDomain domainCond = (TwitterRecordDomain)cond;
            TwitterRecordDomain domainParam = (TwitterRecordDomain)param;
            TwitterRecordDomain queryParam = new TwitterRecordDomain(
                    domainParam.getRecordTime(), domainCond.getTwitterUserId(),
                    domainParam.getTotalTweetCount(), domainParam.getTwitterDisplayId());
            session.update(TwitterRecordSql.update, queryParam);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public TwitterRecordDomain selectOne(IDbDomain param) {
        if(!(param instanceof TwitterRecordDomain)){
            throw new IllegalArgumentException("param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            TwitterRecordDomain domainParam = (TwitterRecordDomain) param;
            return session.selectOne(TwitterRecordSql.selectOne, domainParam);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public List<TwitterRecordDomain> selectMulti(IDbDomain param) {
        if(!(param instanceof TwitterRecordDomain)){
            throw new IllegalArgumentException("param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            TwitterRecordDomain domainParam = (TwitterRecordDomain) param;
            return session.selectList(TwitterRecordSql.selectMulti, domainParam);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }
}
