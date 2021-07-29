package com.github.graycat27.twitterbot.bot;

import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** job間で受け渡しするデータ
 * setter/getterはpackage-privateにする */
public class BotJobData {

    private List<BotUsersDomain> targetUsers = Collections.emptyList();
    void setTargetUsers(List<BotUsersDomain> userList){
        if(userList == null){
            targetUsers = Collections.emptyList();
        }else{
            targetUsers = userList;
        }
    }
    List<BotUsersDomain> getTargetUsers(){
        return new ArrayList<>(targetUsers);
    }

}
