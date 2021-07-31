package com.github.graycat27.twitterbot.bot.job.eachTen;

import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.DbDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import com.github.graycat27.twitterbot.heroku.db.query.DbQuery;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterRecordQuery;
import com.github.graycat27.twitterbot.twitter.api.caller.GetUserInfoApi;
import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.UserInfoData;

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
            ResponseCore<UserInfoData> userData = GetUserInfoApi.getUser(user.getTwUserId());
            TwitterRecordDomain selectParam = new TwitterRecordDomain(
                    null, user.getTwUserId(), null, null, null);
            TwitterRecordDomain record = recordQuery.selectOne(selectParam);

            if(record != null){
                // checkID then update
                doTask4KnownUser(userData, record);
            }else{
                // insert
                doTask4NewUser(userData);
            }

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

    }

    private void doTask4KnownUser(ResponseCore<UserInfoData> userData, TwitterRecordDomain record){
        //TODO make this

        UserInfoData.PublicMetrics metrics = (UserInfoData.PublicMetrics) userData.getData().get("public_metrics");

        int totalTweetCountLatest = (Integer)metrics.get("tweet_count");
        int totalTweetCountBefore = record.getTotalTweetCount();
        int totalTweetCountYesterdayLast = record.getTotalTweetCountAtDate();


        if(totalTweetCountLatest - totalTweetCountYesterdayLast < 0){
            // 総ツイ数が減少しているケース
            //TODO make this
        }else{
            int hundred = checkHundred(totalTweetCountLatest, totalTweetCountBefore, totalTweetCountYesterdayLast);
            if(hundred != 0){

                DbQuery dbQuery = new DbQuery();
                DbDomain.Today dbToday = dbQuery.getToday();
                String today = dbToday.getToday();

                tweetHundred(hundred, today);
            }
        }


        //TODO make this
        TwitterRecordDomain updateInfoDomain = new TwitterRecordDomain(

        );

    }

    /** recordが存在しない新規ユーザ向けの処理 */
    private void doTask4NewUser(ResponseCore<UserInfoData> userData){
        UserInfoData.PublicMetrics metrics = (UserInfoData.PublicMetrics) userData.getData().get("public_metrics");

        TwitterRecordDomain newData = new TwitterRecordDomain(
                null, (String)userData.getData().get("id"),
                (Integer)metrics.get("tweet_count"), (Integer)metrics.get("tweet_count"),
                (String)userData.getData().get("username")
        );
        TwitterRecordQuery recordQuery = new TwitterRecordQuery();
        recordQuery.insert(newData);
    }

    /**
     * 100の倍数回を超えた初回かどうかを判定する。
     * @param now 今回実行時の値
     * @param before 前回実行時の値
     * @param base 昨日末時点の値
     * @return 今回100の倍数を超えた場合、その倍数値。その他の場合、<code>0</code>
     * */
    private int checkHundred(int now, int before, int base){
        int deltaNow = now - base;
        int deltaBefore = before - base;

        if(deltaNow / 100 == deltaBefore / 100){
            return 0;
        }

        return ( deltaNow / 100 ) * 100;
    }

    private void tweetHundred(int amount, String today){
        //TODO make this

        System.out.println("tweet that hundred limitation for "+ amount + ", "+ today);
    }
}
