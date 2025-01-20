package com.github.graycat27.twitterbot.bot.job.eachTen;

import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.Today;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterUserTokenDomain;
import com.github.graycat27.twitterbot.heroku.db.query.DbQuery;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterRecordQuery;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterUserTokenQuery;
import com.github.graycat27.twitterbot.twitter.api.caller.GetUserInfoApi;
import com.github.graycat27.twitterbot.twitter.api.caller.SendTweetApi;
import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.data.UserInfoData;
import com.github.graycat27.twitterbot.utils.TweetTemplate;
import com.github.graycat27.twitterbot.utils.exception.TwitterApiException;

public class UserHundredChecker extends AbstractJob {

    private final BotUsersDomain user;

    // package-private constructor
    UserHundredChecker(BotUsersDomain user){
        this.user = user;
    }

    @Override
    protected void jobTask() {

        try {
            TwitterUserTokenQuery tokenQuery = new TwitterUserTokenQuery();
            TwitterUserTokenDomain tokenRecord = tokenQuery.selectOne(new TwitterUserTokenDomain(user.getTwUserId()));
            ResponseCore<UserInfoData> userData = GetUserInfoApi.getUser(user.getTwUserId(), tokenRecord.getToken(), true);
            TwitterRecordQuery recordQuery = new TwitterRecordQuery();
            TwitterRecordDomain selectParam = new TwitterRecordDomain(
                    user.getTwUserId(), null, null, null, null, null);
            TwitterRecordDomain record = recordQuery.selectOne(selectParam);

            if(record != null){
                // checkID then update
                doTask4KnownUser(userData, record);
            }else{
                // insert
                doTask4NewUser(userData);
            }

        } catch (TwitterApiException e) {
            e.printStackTrace();
        }

    }

    private void doTask4KnownUser(ResponseCore<UserInfoData> userData, TwitterRecordDomain record){
        UserInfoData.PublicMetrics metrics = (UserInfoData.PublicMetrics) userData.getData().get("public_metrics");

        int totalTweetCountLatest = (Integer)metrics.get("tweet_count");
        int totalTweetCountBefore = record.getTotalTweetCount();
        int totalTweetCountYesterdayLast = record.getTotalTweetCountAtDate();

        int hundred = checkHundred(totalTweetCountLatest, totalTweetCountBefore, totalTweetCountYesterdayLast);
        if(hundred != 0){

            DbQuery dbQuery = new DbQuery();
            Today dbToday = dbQuery.getToday();
            String today = dbToday.getToday();

            tweetHundred(hundred, today, record.getTwDisplayId());
        }

        TwitterRecordQuery recordQuery = new TwitterRecordQuery();
        TwitterRecordDomain inDomain = new TwitterRecordDomain(
                (String)userData.getData().get("id"),
                null,   //CURRENT_TIMESTAMP
                totalTweetCountLatest,
                null, null,
                (String)userData.getData().get("username")
            );
        recordQuery.update(inDomain, inDomain);

    }

    /** recordが存在しない新規ユーザ向けの処理 */
    private void doTask4NewUser(ResponseCore<UserInfoData> userData){
        UserInfoData.PublicMetrics metrics = (UserInfoData.PublicMetrics) userData.getData().get("public_metrics");

        TwitterRecordDomain newData = new TwitterRecordDomain(
                (String)userData.getData().get("id"),
                null, (Integer)metrics.get("tweet_count"),
                null, (Integer)metrics.get("tweet_count"),
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

        if(now < before){
            return 0;   //ツイ消しorRTの垢消し等が疑われるケース
        }

        if(deltaNow / 100 == deltaBefore / 100){
            return 0;
        }

        return ( deltaNow / 100 ) * 100;
    }

    private void tweetHundred(int amount, String today, String displayId){
        String tweetText = String.format(TweetTemplate.hundred, today, amount) + "@"+ displayId;
        SendTweetApi.sendTweet(user.getTwUserId(), tweetText);

    }
}
