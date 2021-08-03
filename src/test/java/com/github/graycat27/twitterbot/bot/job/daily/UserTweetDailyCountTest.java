package com.github.graycat27.twitterbot.bot.job.daily;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserTweetDailyCountTest {

    /** テスト対象がprivateメソッドなので、リフレクションによる呼出しをする処理 */
    private boolean isNewDate(Timestamp latest, Timestamp yesterday){
        UserTweetDailyCount target = new UserTweetDailyCount(null);
        boolean result;
        try{
            Method method = UserTweetDailyCount.class.getDeclaredMethod("isNewDate", TwitterRecordDomain.class);
            method.setAccessible(true);

            TwitterRecordDomain param = new TwitterRecordDomain(
                    null, latest, 0, yesterday, 0, "test"
            );
            result = (boolean) method.invoke(target, param);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Test
    public void isNewDate_sameTimestamp(){
        Timestamp timestamp = new Timestamp(2021, 8, 1, 12, 34, 56, 0);
        assertFalse(isNewDate(timestamp, timestamp));
    }
    @Test
    public void isNewDate_sameDate(){
        Timestamp latest = new Timestamp(2021, 8, 1, 12, 34, 56, 0);
        Timestamp yesterday = new Timestamp(2021, 8, 1, 11, 22, 33, 0);
        assertFalse(isNewDate(latest, yesterday));
    }
    @Test
    public void isNewDate_sameDate_after(){
        Timestamp latest = new Timestamp(2021, 8, 1, 16, 18, 20, 0);
        Timestamp yesterday = new Timestamp(2021, 8, 1, 15, 17, 19, 0);
        assertFalse(isNewDate(latest, yesterday));
    }

    @Test
    public void isNewDate_isNew(){
        Timestamp latest = new Timestamp(2021, 8, 1, 15, 0, 0, 0);
        Timestamp yesterday = new Timestamp(2021, 8, 1, 14, 59, 59, 0);
        assertTrue(isNewDate(latest, yesterday));
    }
    @Test
    public void isNewDate_diffDate(){
        Timestamp latest = new Timestamp(2021, 8, 1, 15, 0, 0, 0);
        Timestamp yesterday = new Timestamp(2021, 7, 31, 15, 0, 0, 0);
        assertTrue(isNewDate(latest, yesterday));
    }

    @Test
    public void isNewDate_diffDateSameDay(){
        Timestamp latest = new Timestamp(2021, 8, 1, 14, 59, 59, 0);
        Timestamp yesterday = new Timestamp(2021, 7, 31, 15, 0, 0, 0);
        assertFalse(isNewDate(latest, yesterday));
    }
    @Test
    public void isNewDate_diffDateDiffDay(){
        Timestamp latest = new Timestamp(2021, 8, 1, 15, 0, 0, 0);
        Timestamp yesterday = new Timestamp(2021, 7, 31, 14, 59, 59, 0);
        assertTrue(isNewDate(latest, yesterday));
    }


}