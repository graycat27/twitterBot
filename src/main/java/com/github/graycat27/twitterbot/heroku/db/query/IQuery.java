package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;

import java.util.List;

public interface IQuery {

    void insert(IDbDomain param);

    void update(IDbDomain cond, IDbDomain param);

    IDbDomain selectOne(IDbDomain param);

    List<IDbDomain> selectMulti(IDbDomain param);
}
