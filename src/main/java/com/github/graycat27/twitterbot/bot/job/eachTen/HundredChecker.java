package com.github.graycat27.twitterbot.bot.job.eachTen;

import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterRecordQuery;

import java.sql.Timestamp;
import java.util.Random;

public class HundredChecker extends AbstractJob {

    @Override
    public void jobTask() {
        //TODO make this

        TwitterRecordQuery recordQuery = new TwitterRecordQuery();
        Random r = new Random();
        TwitterRecordDomain inDomain = new TwitterRecordDomain(
                new Timestamp(System.nanoTime()),
                "testid2",
                r.nextInt(100000),
                "@testId2");
        System.out.println("update record : "+ inDomain);
        recordQuery.update(inDomain, inDomain);


        /* この処理のフロー
         *
         * targetUsersを読み出す
         *
         * user毎ループ：
         *    ApiCall#getUserData
         *       (@ID, totalTweetCount)
         *    checkID
         *       :if changed = update record
         *    select record
         *    calc nowTotalCnt - record.dayTotalCnt *A
         *    calc record.latestCnt - record.dayTotalCnd *B
         *    %100 ?
         *
         *    ApiCall#sendTweet
         *
         *    update record.latestCnt
         *
         */
    }
}
