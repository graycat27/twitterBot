package com.github.graycat27.twitterbot.bot;

import com.github.graycat27.twitterbot.bot.job.entry.GetTargetList;
import com.github.graycat27.twitterbot.bot.job.eachTen.HundredChecker;
import com.github.graycat27.twitterbot.bot.job.IBatchJob;
import com.github.graycat27.twitterbot.bot.job.daily.TweetDailyCount;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;

import java.util.List;

public class BotTask {

    // field
    static private BotJobData data;

    // constructor
    public BotTask(){
        data = new BotJobData();
    }

    // method
    public void run(){
        IBatchJob job;
        job = new GetTargetList();
        job.run();

        job = new HundredChecker();
        job.run();

        job = new TweetDailyCount();
        job.run();
    }

    /* data method */
    private static void validateAccess() throws IllegalAccessException {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        try {
            /*
            trace[0] = Thread
            trace[1] = thisClass#thisMethod
            trace[2] = this#set/get
            trace[3] = caller job class
             */
            Object caller = Class.forName(trace[3].getClassName()).newInstance();
            if(caller instanceof IBatchJob){
                System.out.println("data access validate is success");
            }else{
                throw new IllegalAccessException();
            }
        } catch (ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            throw new IllegalAccessException();
        }
    }
    public static void setUserList(List<BotUsersDomain> list){
        try {
            validateAccess();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        data.setTargetUsers(list);
    }
    public static List<BotUsersDomain> getUserList(){
        try{
            validateAccess();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return data.getTargetUsers();
    }

}
