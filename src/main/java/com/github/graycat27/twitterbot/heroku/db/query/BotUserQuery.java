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
            session.insert(BotUserSql.insert, domainParam);
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
            session.update(BotUserSql.update, queryParam);
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
            return session.selectOne(BotUserSql.selectOne, domainParam);
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
            return session.selectList(BotUserSql.selectMulti, domainParam);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }
}
