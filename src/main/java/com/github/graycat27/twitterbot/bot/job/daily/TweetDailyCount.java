package com.github.graycat27.twitterbot.bot.job.daily;

import com.github.graycat27.twitterbot.bot.BotTask;
import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterRecordQuery;
import com.github.graycat27.twitterbot.utils.ListUtil;

import java.util.List;

public class TweetDailyCount extends AbstractJob {

    @Override
    public void jobTask() {
        // 日付が変わってるかチェックする
        boolean isNewDay = false;
        //TODO make this


        // 変わってる場合
        if(isNewDay) {
            List<BotUsersDomain> targetUsers = BotTask.getUserList();
            for (BotUsersDomain user : targetUsers) {
                UserTweetDailyCount userJob = new UserTweetDailyCount(user);
                userJob.jobTask();
            }
        }
    }
}
