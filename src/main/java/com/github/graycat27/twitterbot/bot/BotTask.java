package com.github.graycat27.twitterbot.bot;

import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.query.BotUserQuery;

public class BotTask {

    public void run(){
        System.out.println("BotTask Start ----->");
        sampleDbIoTask();
        System.out.println("BotTask End -----<");
    }

    private void sampleDbIoTask(){

        BotUsersDomain botUsersDomain;
        BotUserQuery query = new BotUserQuery();
        botUsersDomain = query.selectOne(null);
        System.out.println(botUsersDomain);


    }
}
