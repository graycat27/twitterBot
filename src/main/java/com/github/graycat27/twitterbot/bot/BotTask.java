package com.github.graycat27.twitterbot.bot;

import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.IDbDomain;
import com.github.graycat27.twitterbot.heroku.db.query.BotUserQuery;
import com.github.graycat27.twitterbot.utils.ListUtil;

import java.util.List;

public class BotTask {

    public void run(){
        System.out.println("BotTask Start ----->");
        sampleDbIoTask();
        System.out.println("BotTask End -----<");
    }

    private void sampleDbIoTask(){

        BotUserQuery query = new BotUserQuery();
        List<BotUsersDomain> resultList = query.selectMulti(null);
        ListUtil.printList(resultList);


    }
}
