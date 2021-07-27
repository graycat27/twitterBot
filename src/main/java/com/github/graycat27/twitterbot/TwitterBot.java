package com.github.graycat27.twitterbot;

import com.github.graycat27.twitterbot.bot.BotTask;

public class TwitterBot {

    public static void main(String[] args){
        System.out.println("Bot Start ----->");
        BotTask task = new BotTask();
        task.run();
        System.out.println("Bot End -----<");
    }
}
