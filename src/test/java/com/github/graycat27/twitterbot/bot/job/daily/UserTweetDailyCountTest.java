package com.github.graycat27.twitterbot.bot.job.daily;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Calendar;

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

    /** Timestampの非推奨コンストラクタの置換　*/
    private Timestamp getTimeStamp(int year, int month, int date, int hour, int minute, int second, int nano){
        Timestamp ts = null;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, nano / 10);
        return new Timestamp(c.getTimeInMillis());
    }

    @Test
    public void isNewDate_sameTimestamp(){
        Timestamp timestamp = getTimeStamp(2021, 8, 1, 12, 34, 56, 0);
        assertFalse(isNewDate(timestamp, timestamp));
    }
    @Test
    public void isNewDate_sameDate(){
        Timestamp latest = getTimeStamp(2021, 8, 1, 12, 34, 56, 0);
        Timestamp yesterday = getTimeStamp(2021, 8, 1, 11, 22, 33, 0);
        assertFalse(isNewDate(latest, yesterday));
    }
    @Test
    public void isNewDate_sameDate_after(){
        Timestamp latest = getTimeStamp(2021, 8, 1, 16, 18, 20, 0);
        Timestamp yesterday = getTimeStamp(2021, 8, 1, 15, 17, 19, 0);
        assertFalse(isNewDate(latest, yesterday));
    }

    @Test
    public void isNewDate_isNew(){
        Timestamp latest = getTimeStamp(2021, 8, 1, 15, 0, 0, 0);
        Timestamp yesterday = getTimeStamp(2021, 8, 1, 14, 59, 59, 0);
        assertTrue(isNewDate(latest, yesterday));
    }
    @Test
    public void isNewDate_diffDate(){
        Timestamp latest = getTimeStamp(2021, 8, 1, 15, 0, 0, 0);
        Timestamp yesterday = getTimeStamp(2021, 7, 31, 15, 0, 0, 0);
        assertTrue(isNewDate(latest, yesterday));
    }

    @Test
    public void isNewDate_diffDateSameDay(){
        Timestamp latest = getTimeStamp(2021, 8, 1, 14, 59, 59, 0);
        Timestamp yesterday = getTimeStamp(2021, 7, 31, 15, 0, 0, 0);
        assertFalse(isNewDate(latest, yesterday));
    }
    @Test
    public void isNewDate_diffDateDiffDay(){
        Timestamp latest = getTimeStamp(2021, 8, 1, 15, 0, 0, 0);
        Timestamp yesterday = getTimeStamp(2021, 7, 31, 14, 59, 59, 0);
        assertTrue(isNewDate(latest, yesterday));
    }

    @Test
    public void isNewDate_diffDateTwoDiffDay(){
        Timestamp latest = getTimeStamp(2021, 8, 1, 14, 59, 59, 0);
        Timestamp dayLogged = getTimeStamp(2021, 7, 30, 15, 0, 0, 0);
        assertTrue(isNewDate(latest, dayLogged));
    }
    @Test
    public void isNewDate_diffDateTwoDiffDayOverMonth(){
        Timestamp latest = getTimeStamp(2021, 8, 2, 14, 59, 59, 0);
        Timestamp dayLogged = getTimeStamp(2021, 7, 31, 15, 0, 0, 0);
        assertTrue(isNewDate(latest, dayLogged));
    }


}