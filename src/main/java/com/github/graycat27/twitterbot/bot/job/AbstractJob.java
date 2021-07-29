package com.github.graycat27.twitterbot.bot.job;

public abstract class AbstractJob implements IBatchJob {

    @Override
    public void run() {
        startLog();
        try {
            jobTask();
        }catch(Exception e){
            System.out.println("Error occurred on task "+ this.getClass().getName());
            e.printStackTrace();
        }
        endLog();
    }

    protected void startLog(){
        System.out.println("job start -- "+ this.getClass().getName() +" -- ----->");
    }

    protected abstract void jobTask();

    protected void endLog(){
        System.out.println("job end -- "+ this.getClass().getName() +" -- -----<");
    }

}
