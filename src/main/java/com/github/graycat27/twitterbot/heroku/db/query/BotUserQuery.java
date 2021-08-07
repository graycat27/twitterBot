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
            String sql = BotUserSql.insert;
            BotUsersDomain domainParam = (BotUsersDomain) param;
            logParamObject(sql, domainParam);
            int result = session.insert(sql, domainParam);
            logResultObject(sql, result);
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
            String sql = BotUserSql.update;
            BotUsersDomain domainCond = (BotUsersDomain)cond;
            BotUsersDomain queryParam = new BotUsersDomain(domainCond.getTwUserId());
            logParamObject(sql, queryParam);
            int result = session.update(sql, queryParam);
            logResultObject(sql, result);
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
            String sql = BotUserSql.selectOne;
            BotUsersDomain domainParam = (BotUsersDomain)param;
            logParamObject(sql, domainParam);
            BotUsersDomain result = session.selectOne(sql, domainParam);
            logResultObject(sql, result);
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
            String sql = BotUserSql.selectMulti;
            BotUsersDomain domainParam = (BotUsersDomain)param;
            logParamObject(sql, domainParam);
            List<BotUsersDomain> result = session.selectList(sql, domainParam);
            logResultObject(sql, result);
            return result;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    public void deleteLogical(BotUsersDomain param){
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            String sql = BotUserSql.deleteLogical;
            logParamObject(sql, param);
            int result = session.update(sql, param);
            logResultObject(sql, result);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    public void restoreDeletedUser(BotUsersDomain param){
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            String sql = BotUserSql.restoreDeletedUser;
            logParamObject(sql, param);
            int result = session.update(sql, param);
            logResultObject(sql, result);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    public BotUsersDomain selectThoughDeleted(BotUsersDomain param){
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            String sql = BotUserSql.selectThoughDeleted;
            logParamObject(sql, param);
            BotUsersDomain result = session.selectOne(sql, param);
            logResultObject(sql, result);
            return result;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }
}
