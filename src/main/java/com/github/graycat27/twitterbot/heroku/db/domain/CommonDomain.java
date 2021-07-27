package com.github.graycat27.twitterbot.heroku.db.domain;

import java.sql.Timestamp;

public abstract class CommonDomain implements IDbDomain {

    private Timestamp createdDate;
    public Timestamp getCreatedDate(){
        return createdDate;
    }

    private Timestamp updateDate;
    public Timestamp getUpdateDate(){
        return updateDate;
    }

    private boolean isDeleted;
    public boolean isDeleted(){
        return isDeleted;
    }

}
