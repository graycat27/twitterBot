package com.github.graycat27.twitterbot.bot;

import com.github.graycat27.twitterbot.bot.job.GetTargetList;
import com.github.graycat27.twitterbot.bot.job.HundredChecker;
import com.github.graycat27.twitterbot.bot.job.IBatchJob;
import com.github.graycat27.twitterbot.bot.job.TweetDailyCount;

public class BotTask {

    public void run(){
        IBatchJob job;
        job = new GetTargetList();
        job.run();

        job = new HundredChecker();
        job.run();

        job = new TweetDailyCount();
        job.run();
    }

}
