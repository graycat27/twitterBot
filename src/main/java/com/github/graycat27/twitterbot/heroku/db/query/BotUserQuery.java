package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.DBConnection;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.sql.BotUserSql;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

public class BotUserQuery extends QueryRunnable{
    @Override
    public void insert(IDbDomain param) {
        if(param != null && !(param instanceof BotUsersDomain)){
            throw new IllegalArgumentException("param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            BotUsersDomain domainParam = (BotUsersDomain) param;
            logParamObject(domainParam);
            int result = session.insert(BotUserSql.insert, domainParam);
            logResultObject(result);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public void update(IDbDomain cond, IDbDomain param) {
        if(cond != null && !(cond instanceof BotUsersDomain) || param != null && !(param instanceof BotUsersDomain)){
            throw new IllegalArgumentException("cond or param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            BotUsersDomain domainCond = (BotUsersDomain)cond;
            BotUsersDomain queryParam = new BotUsersDomain(domainCond.getTwUserId());
            logParamObject(queryParam);
            int result = session.update(BotUserSql.update, queryParam);
            logResultObject(result);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public BotUsersDomain selectOne(IDbDomain param) {
        if(param != null && !(param instanceof BotUsersDomain)){
            throw new IllegalArgumentException("param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            BotUsersDomain domainParam = (BotUsersDomain)param;
            logParamObject(domainParam);
            BotUsersDomain result = session.selectOne(BotUserSql.selectOne, domainParam);
            logResultObject(result);
            return result;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public List<BotUsersDomain> selectMulti(IDbDomain param) {
        if(param != null && !(param instanceof BotUsersDomain)){
            throw new IllegalArgumentException("param is wrong Type");
        }
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            BotUsersDomain domainParam = (BotUsersDomain)param;
            logParamObject(domainParam);
            List<BotUsersDomain> result = session.selectList(BotUserSql.selectMulti, domainParam);
            logResultObject(result);
            return result;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    public void deleteLogical(BotUsersDomain param){
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            logParamObject(param);
            int result = session.update(BotUserSql.deleteLogical, param);
            logResultObject(result);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    public void restoreDeletedUser(BotUsersDomain param){
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            logParamObject(param);
            int result = session.update(BotUserSql.restoreDeletedUser, param);
            logResultObject(result);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    public BotUsersDomain selectThoughDeleted(BotUsersDomain param){
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            logParamObject(param);
            BotUsersDomain result = session.selectOne(BotUserSql.selectThoughDeleted, param);
            logResultObject(result);
            return result;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }
}
