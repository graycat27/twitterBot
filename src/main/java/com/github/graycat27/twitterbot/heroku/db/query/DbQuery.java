package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.DBConnection;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.Today;
import com.github.graycat27.twitterbot.heroku.db.sql.DbQuerySql;
import com.github.graycat27.twitterbot.heroku.db.sql.SqlKey;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

public class DbQuery extends QueryRunnable {

    public Today getToday(){
        try(SqlSession session = factory.openSession(DBConnection.getConnection())){
            SqlKey sql = DbQuerySql.selectTodayString;
            logParamObject(sql, null);
            Today result = session.selectOne(sql.val(), null);
            logResultObject(sql, result);
            return result;
        } catch (SQLException sqlEx) {
            throw new RuntimeException(sqlEx);
        }
    }

    @Override
    public void insert(IDbDomain param) {
       throw new UnsupportedOperationException();
    }

    @Override
    public void update(IDbDomain cond, IDbDomain param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IDbDomain selectOne(IDbDomain param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<? extends IDbDomain> selectMulti(IDbDomain param) {
        throw new UnsupportedOperationException();
    }

}
