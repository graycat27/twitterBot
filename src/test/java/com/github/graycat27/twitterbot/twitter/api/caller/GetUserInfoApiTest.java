package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.UserInfoData;
import com.github.graycat27.twitterbot.utils.JsonUtil;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class GetUserInfoApiTest {

    @Test
    public void fromJson(){

        String input = "{\"data\":{\"pinned_tweet_id\":\"1184079696876556288\",\"description\":\"灰猫\"," +
                "\"id\":\"466278557\",\"public_metrics\":{\"tweet_count\":233141},\"name\":\"灰\"}}";

        Type dataType = new TypeToken<ResponseCore<UserInfoData>>(){}.getType();
        ResponseCore<UserInfoData> data = JsonUtil.getObjectFromJsonStr(input, dataType);

        System.out.println(data);

        UserInfoData.PublicMetrics innerData = (UserInfoData.PublicMetrics) data.getData().get("public_metrics");
        assertAll(
                () -> assertEquals("466278557", data.getData().get("id")),
                () -> assertEquals("1184079696876556288", data.getData().get("pinned_tweet_id")),
                () -> assertEquals("灰猫", data.getData().get("description")),
                () -> assertEquals(233141, innerData.get("tweet_count"))
        );
    }


}