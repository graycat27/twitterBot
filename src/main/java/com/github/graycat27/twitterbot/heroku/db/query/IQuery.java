package com.github.graycat27.twitterbot.heroku.db.query;

import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;

public interface IQuery {

    void insert(IDbDomain param);

    void update(IDbDomain cond, IDbDomain param);

    IDbDomain selectOne(IDbDomain param);

    IDbDomain selectMulti(IDbDomain param);
}
