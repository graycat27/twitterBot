package com.github.graycat27.twitterbot.bot.job.eachTen;

import com.github.graycat27.twitterbot.bot.BotTask;
import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.domain.TwitterRecordDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterRecordQuery;
import com.github.graycat27.twitterbot.twitter.api.caller.GetUserInfo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

public class HundredChecker extends AbstractJob {

    @Override
    public void jobTask() {

        List<BotUsersDomain> targetUsers = BotTask.getUserList();
        for(BotUsersDomain user : targetUsers){
            UserHundredChecker innerTask = new UserHundredChecker(user);
            innerTask.run();
        }

    }
}
