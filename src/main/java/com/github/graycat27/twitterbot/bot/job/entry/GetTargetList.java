package com.github.graycat27.twitterbot.bot.job.entry;

import com.github.graycat27.twitterbot.bot.BotTask;
import com.github.graycat27.twitterbot.bot.job.AbstractJob;
import com.github.graycat27.twitterbot.heroku.db.domain.BotUsersDomain;
import com.github.graycat27.twitterbot.heroku.db.query.BotUserQuery;
import com.github.graycat27.twitterbot.utils.ListUtil;

import java.util.Collections;
import java.util.List;

public class GetTargetList extends AbstractJob {

    @Override
    public void jobTask() {
        BotUserQuery query = new BotUserQuery();
        List<BotUsersDomain> resultList = query.selectMulti(null);
        Collections.shuffle(resultList);
        ListUtil.printList(resultList);

        BotTask.setUserList(resultList);

    }
}
