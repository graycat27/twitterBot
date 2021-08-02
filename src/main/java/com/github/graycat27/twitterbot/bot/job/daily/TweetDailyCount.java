package com.github.graycat27.twitterbot.bot.job.daily;

import com.github.graycat27.twitterbot.bot.BotTask;
import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;

import java.util.List;

public class TweetDailyCount extends AbstractJob {

    @Override
    public void jobTask() {
        List<BotUsersDomain> targetUsers = BotTask.getUserList();
        for (BotUsersDomain user : targetUsers) {
            UserTweetDailyCount userJob = new UserTweetDailyCount(user);
            userJob.jobTask();
        }
    }
}
