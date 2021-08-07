package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.DBConnection;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import com.github.graycat27.twitterbot.heroku.db.sql.SqlKey;
import com.github.graycat27.twitterbot.heroku.db.sql.TwitterRecordSql;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

public class TwitterRecordQuery extends QueryRunnable{
    @Override
    public void insert(IDbDomain param) {
        if(param != null && !(param instanceof TwitterRecordDomain)){
            throw new IllegalArgumentException("param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            SqlKey sql = TwitterRecordSql.insert;
            TwitterRecordDomain domainParam = (TwitterRecordDomain) param;
            logParamObject(sql, domainParam);
            int result = session.insert(sql.val(), domainParam);
            logResultObject(sql, result);
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
            SqlKey sql = TwitterRecordSql.update;
            TwitterRecordDomain domainCond = (TwitterRecordDomain)cond;
            TwitterRecordDomain domainParam = (TwitterRecordDomain)param;
            TwitterRecordDomain queryParam = new TwitterRecordDomain(
                    domainCond.getTwUserId(),
                    domainParam.getRecordTime(), domainParam.getTotalTweetCount(),
                    domainParam.getDateRecordTime(), domainParam.getTotalTweetCountAtDate(),
                    domainParam.getTwDisplayId());
            logParamObject(sql, queryParam);
            int result = session.update(sql.val(), queryParam);
            logResultObject(sql, result);
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
            SqlKey sql = TwitterRecordSql.selectOne;
            TwitterRecordDomain domainParam = (TwitterRecordDomain) param;
            logParamObject(sql, domainParam);
            TwitterRecordDomain result = session.selectOne(sql.val(), domainParam);
            logResultObject(sql, result);
            return result;
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
            SqlKey sql = TwitterRecordSql.selectMulti;
            TwitterRecordDomain domainParam = (TwitterRecordDomain) param;
            logParamObject(sql, domainParam);
            List<TwitterRecordDomain> result = session.selectList(sql.val(), domainParam);
            logResultObject(sql, result);
            return result;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    public void updateDaily(TwitterRecordDomain updateInfo){
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            SqlKey sql = TwitterRecordSql.updateDaily;
            TwitterRecordDomain queryParam = new TwitterRecordDomain(
                    updateInfo.getTwUserId(), null, null,
                    updateInfo.getDateRecordTime(), updateInfo.getTotalTweetCountAtDate(), null);
            logParamObject(sql, queryParam);
            int result = session.update(sql.val(), queryParam);
            logResultObject(sql, result);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }
}
