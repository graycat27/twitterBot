package com.github.graycat27.twitterbot.bot.job;

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


    }
}
