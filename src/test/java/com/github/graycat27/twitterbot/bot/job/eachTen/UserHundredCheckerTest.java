package com.github.graycat27.twitterbot.bot.job.eachTen;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class UserHundredCheckerTest {

    /** テスト対象がprivateメソッドなので、リフレクションによる呼出しをする処理 */
    private int checkHundred(int now, int before, int base){

        UserHundredChecker target = new UserHundredChecker(null);
        int result;
        try {
            Method method = UserHundredChecker.class.getDeclaredMethod("checkHundred", int.class, int.class, int.class);
            method.setAccessible(true);
            result = (int)method.invoke(target, now, before, base);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Test
    public void hundredTester1(){
        assertEquals(0, checkHundred(0, 0, 0));
    }

    @Test
    public void hundredTester2(){
        assertEquals(100, checkHundred(100, 0, 0));
    }

    @Test
    public void hundredTester3(){
        assertEquals(200, checkHundred(200, 0, 0));
    }

    @Test
    public void hundredTester4(){
        assertEquals(500, checkHundred(700, 699, 200));
    }

    @Test
    public void hundredTesterLimit1(){
        assertEquals(100, checkHundred(100, 99, 0));
    }
    @Test
    public void hundredTesterLimit2(){
        assertEquals(100, checkHundred(200, 199, 100));
    }

    @Test
    public void hundredTesterLimit3(){
        assertEquals(0, checkHundred(101, 100, 2));
    }

}