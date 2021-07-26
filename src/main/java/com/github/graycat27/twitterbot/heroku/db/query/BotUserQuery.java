package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.sql.BotUserSql;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BotUserQuery extends QueryRunnable{
    @Override
    public void insert(IDbDomain param) {

    }

    @Override
    public void update(IDbDomain cond, IDbDomain param) {

    }

    @Override
    public BotUsersDomain selectOne(IDbDomain param) {
        try(SqlSession session = factory.openSession()){
            return session.selectOne(BotUserSql.selectAll);
        }
    }

    @Override
    public List<IDbDomain> selectMulti(IDbDomain param) {
        try(SqlSession session = factory.openSession()){
            return session.selectList(BotUserSql.selectAll);
        }
    }
}
