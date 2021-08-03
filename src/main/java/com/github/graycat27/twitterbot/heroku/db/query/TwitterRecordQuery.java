package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.DBConnection;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
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
        if( cond != null && !(cond instanceof TwitterRecordDomain) ||
            param != null && !(param instanceof TwitterRecordDomain)){
            throw new IllegalArgumentException("cond or param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            TwitterRecordDomain domainCond = (TwitterRecordDomain)cond;
            TwitterRecordDomain domainParam = (TwitterRecordDomain)param;
            TwitterRecordDomain queryParam = new TwitterRecordDomain(
                    domainCond.getTwUserId(),
                    domainParam.getRecordTime(), domainParam.getTotalTweetCount(),
                    domainParam.getDateRecordTime(), domainParam.getTotalTweetCountAtDate(),
                    domainParam.getTwDisplayId());
            session.update(TwitterRecordSql.update, queryParam);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public TwitterRecordDomain selectOne(IDbDomain param) {
        if(param != null && !(param instanceof TwitterRecordDomain)){
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
        if(param != null && !(param instanceof TwitterRecordDomain)){
            throw new IllegalArgumentException("param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            TwitterRecordDomain domainParam = (TwitterRecordDomain) param;
            return session.selectList(TwitterRecordSql.selectMulti, domainParam);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    public void updateDaily(TwitterRecordDomain updateInfo){
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            TwitterRecordDomain queryParam = new TwitterRecordDomain(
                    updateInfo.getTwUserId(), null, null,
                    updateInfo.getDateRecordTime(), updateInfo.getTotalTweetCountAtDate(), null);
            session.update(TwitterRecordSql.updateDaily, queryParam);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }
}
