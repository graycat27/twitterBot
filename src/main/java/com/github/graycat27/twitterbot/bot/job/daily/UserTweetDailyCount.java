package com.github.graycat27.twitterbot.bot.job.daily;

import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterRecordQuery;
import com.github.graycat27.twitterbot.twitter.api.caller.SendTweetApi;
import com.github.graycat27.twitterbot.utils.TweetTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

        TwitterRecordQuery recordQuery = new TwitterRecordQuery();
        TwitterRecordDomain recordParamDomain = new TwitterRecordDomain(
                user.getTwUserId(),
                null, null, null, null, null
        );
        final TwitterRecordDomain recordResult = recordQuery.selectOne(recordParamDomain);
        if(isNewDate(recordResult)){
            try {
                tweetDailyResult(recordResult);
            }catch(Exception e){
                System.err.println("failed to tweet daily result due to Exception "+ e.getMessage());
                System.err.println(recordResult);
            }
            updateDailyData(recordResult);
        }

    }

    private boolean isNewDate(final TwitterRecordDomain record){
        //Hundredで更新されてる最新
        Calendar.Builder latestBuilder = new Calendar.Builder().setInstant(record.getRecordTime());
        final Calendar latest = latestBuilder.build();

        //日次処理で更新された日付
        Calendar.Builder yesterdayBuilder = new Calendar.Builder().setInstant(record.getDateRecordTime());
        final Calendar yesterday = yesterdayBuilder.build();

        final int latestDate = latest.get(Calendar.MONTH)*100 + latest.get(Calendar.DATE);
        final int latestHour = latest.get(Calendar.HOUR_OF_DAY);
        final int yesterdayDate = yesterday.get(Calendar.MONTH)*100 + yesterday.get(Calendar.DATE);
        final int yesterdayHour = yesterday.get(Calendar.HOUR_OF_DAY);

        final int JST = 9;
        final int DAY_BORDER = 24 - JST;    //15:00UTCがJSTの日界

        if(yesterdayDate == latestDate){
            return (yesterdayHour < DAY_BORDER && DAY_BORDER <= latestHour);
        }else{  // yesterdayDate != latestDate
            if(yesterdayHour < DAY_BORDER){
                return true;
            }else{
                Calendar theDayBefore = latest;
                theDayBefore.add(Calendar.DATE, -1);
                if(yesterday.before(theDayBefore)){
                    return true;
                }
                return (DAY_BORDER <= latestHour);
            }
        }
    }

    private void tweetDailyResult(final TwitterRecordDomain record){
        int delta = record.getTotalTweetCount() - record.getTotalTweetCountAtDate();
        Calendar.Builder yBuild = new Calendar.Builder().setInstant(record.getDateRecordTime());
        Calendar yesterday = yBuild.build();
        yesterday.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yesterdayStr = sdf.format(yesterday.getTime());
        String text = yesterdayStr + "のツイート数は"+ delta + "件でした"
                + "（"+ String.format("%,d",record.getTotalTweetCountAtDate())
                + "->"+ String.format("%,d",record.getTotalTweetCount()) +"）"
                + TweetTemplate.tag + "@"+ record.getTwDisplayId() +" ";
        if(delta <= 0){
            text += TweetTemplate.minus;
        }
        SendTweetApi.sendTweet(record.getTwUserId(), text);
    }

    private void updateDailyData(final TwitterRecordDomain record){
        TwitterRecordQuery recordQuery = new TwitterRecordQuery();
        TwitterRecordDomain updateInfo = new TwitterRecordDomain(
                record.getTwUserId(), null, null,
                record.getRecordTime(), record.getTotalTweetCount(), null
        );
        recordQuery.updateDaily(updateInfo);
    }
}
