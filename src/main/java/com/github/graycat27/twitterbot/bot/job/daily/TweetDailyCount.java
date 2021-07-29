package com.github.graycat27.twitterbot.bot.job.daily;

import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterRecordQuery;
import com.github.graycat27.twitterbot.utils.ListUtil;

import java.util.List;

public class TweetDailyCount extends AbstractJob {

    @Override
    public void jobTask() {
        TwitterRecordQuery recordQuery = new TwitterRecordQuery();
        List<TwitterRecordDomain> recordResult = recordQuery.selectMulti(null);
        ListUtil.printList(recordResult);

        //TODO make this


        /* この処理のフロー
         *
         * targetUser を読み出す
         * TwitterUserIdごとにループ：
         *    select record
         *    ApiCall#getUserData
         *
         *
         */

    }
}
