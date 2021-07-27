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
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            session.insert(BotUserSql.insert, param);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public void update(IDbDomain cond, IDbDomain param) {
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
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            return session.selectOne(BotUserSql.selectOne, param);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public List<BotUsersDomain> selectMulti(IDbDomain param) {
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            return session.selectList(BotUserSql.selectAll);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }
}
