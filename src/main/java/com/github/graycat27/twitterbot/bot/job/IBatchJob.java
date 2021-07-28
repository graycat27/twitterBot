package com.github.graycat27.twitterbot.bot.job;

import com.github.graycat27.twitterbot.utils.TaskStartEndLogging;

public interface IBatchJob {

    @TaskStartEndLogging
    void run();
}
