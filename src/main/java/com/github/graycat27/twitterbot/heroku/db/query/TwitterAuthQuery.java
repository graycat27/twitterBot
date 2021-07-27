package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.DBConnection;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.sql.BotUserSql;
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
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            return session.selectOne(TwitterAuthSql.selectOne, param);
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public List<TwitterAuthDomain> selectMulti(IDbDomain param) {
        return new ArrayList<TwitterAuthDomain>(){{add(selectOne(param));}};
    }
}
