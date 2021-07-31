package com.github.graycat27.twitterbot.bot.job.eachTen;

import com.github.graycat27.twitterbot.bot.BotTask;
import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;

import java.util.List;

public class HundredChecker extends AbstractJob {

    @Override
    public void jobTask() {

        List<BotUsersDomain> targetUsers = BotTask.getUserList();
        for(BotUsersDomain user : targetUsers){
            UserHundredChecker innerTask = new UserHundredChecker(user);
            innerTask.run();
        }

    }
}
