package com.github.graycat27.twitterbot.bot.job.eachTen;

import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterRecordQuery;
import com.github.graycat27.twitterbot.twitter.api.caller.GetUserInfoApi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Random;

public class UserHundredChecker extends AbstractJob {

    private BotUsersDomain user;

    // package-private constructor
    UserHundredChecker(BotUsersDomain user){
        this.user = user;
    }

    @Override
    protected void jobTask() {

        TwitterRecordQuery recordQuery = new TwitterRecordQuery();
        Random r = new Random();
        TwitterRecordDomain inDomain = new TwitterRecordDomain(
                new Timestamp(System.nanoTime()),
                "testid2",
                r.nextInt(100000),
                "@testId2");
        System.out.println("update record : "+ inDomain);
        recordQuery.update(inDomain, inDomain);


        /* user毎ループ：
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
                */

        try {
            GetUserInfoApi.getUser("466278557");    //FIXME gray_cat27 のIDを決め打ちしている
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

    }
}
