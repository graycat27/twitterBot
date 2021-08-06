package com.github.graycat27.twitterbot.bot.job;

public abstract class AbstractJob implements IBatchJob {

    @Override
    public void run() {
        startLog();
        try {
            jobTask();
        }catch(Exception e){
            System.err.println("Error occurred on task "+ this.getClass().getName());
            System.err.println(e.getMessage());
            e.printStackTrace();
            abEndLog();
            return;
        }
        endLog();
    }

    private void startLog(){
        System.out.println("----- job start -- "+ this.getClass().getName() +" -- ----->");
    }

    protected abstract void jobTask();

    protected void abEndLog(){
        System.out.println("******** job end with Exception ** "+ this.getClass().getName() +" ** *****<");
    }

    private void endLog(){
        System.out.println("----- job end -- "+ this.getClass().getName() +" -- -----<");
    }

}
