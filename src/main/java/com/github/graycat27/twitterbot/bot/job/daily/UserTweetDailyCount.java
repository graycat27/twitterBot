package com.github.graycat27.twitterbot.bot.job.daily;

import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;

public class UserTweetDailyCount extends AbstractJob {

    private final BotUsersDomain user;

    // package-private constructor
    UserTweetDailyCount(BotUsersDomain user){
        this.user = user;
    }

    /**
     * 日付が変わっている場合、
     * ユーザ毎に日次の結果をツイートする
     * recordを更新する
     */
    @Override
    protected void jobTask() {
        //TODO make this

        /*
         * select record by userID
         * compare timestamp to day changed
         * if isNewDayArrived
         *    tweet daily result
         *    update record
         */

    }
}
