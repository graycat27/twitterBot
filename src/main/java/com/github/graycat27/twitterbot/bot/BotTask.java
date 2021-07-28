package com.github.graycat27.twitterbot.bot;

import com.github.graycat27.twitterbot.bot.job.GetTargetList;
import com.github.graycat27.twitterbot.bot.job.HundredChecker;
import com.github.graycat27.twitterbot.bot.job.IBatchJob;
import com.github.graycat27.twitterbot.bot.job.TweetDailyCount;
import com.github.graycat27.twitterbot.utils.TaskStartEndLogging;

public class BotTask {

    @TaskStartEndLogging
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
