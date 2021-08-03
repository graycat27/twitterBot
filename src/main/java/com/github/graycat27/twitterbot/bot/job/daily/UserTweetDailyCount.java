package com.github.graycat27.twitterbot.bot.job.daily;

import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterRecordQuery;

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
        //TODO make this
        /*
         * select record by userID
         * compare timestamp to day changed
         * if isNewDayArrived
         *    tweet daily result
         *    update record
         */

        TwitterRecordQuery recordQuery = new TwitterRecordQuery();
        TwitterRecordDomain recordParamDomain = new TwitterRecordDomain(
                user.getTwUserId(),
                null, null, null, null, null
        );
        final TwitterRecordDomain recordResult = recordQuery.selectOne(recordParamDomain);

        if(isNewDate(recordResult)){
            tweetDailyResult();
            updateDailyData(recordResult);
        }

    }

    private boolean isNewDate(final TwitterRecordDomain record){
        //Hundredで更新されてる最新
        Calendar.Builder latestBuilder = new Calendar.Builder().setInstant(record.getRecordTime());
        Calendar latest = latestBuilder.build();

        //日次処理で更新された日付
        Calendar.Builder yesterdayBuilder = new Calendar.Builder().setInstant(record.getDateRecordTime());
        Calendar yesterday = yesterdayBuilder.build();

        final int latestDate = latest.get(Calendar.DATE);
        final int latestHour = latest.get(Calendar.HOUR_OF_DAY);
        final int yesterdayDate = yesterday.get(Calendar.DATE);
        final int yesterdayHour = yesterday.get(Calendar.HOUR_OF_DAY);

        final int JST = 9;
        final int DAY_BORDER = 24 - JST;    //15:00UTCがJSTの日界

        if(yesterdayDate == latestDate){
            if( (yesterdayHour >= DAY_BORDER && latestHour >= DAY_BORDER) ||
                (yesterdayHour < DAY_BORDER && latestHour < DAY_BORDER)
            ) {
                return false;
            }
            return true;
        }else{  // yesterdayDate != latestDate
            return latestHour >= DAY_BORDER;
        }
    }

    private void tweetDailyResult(){
        //TODO make this
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
