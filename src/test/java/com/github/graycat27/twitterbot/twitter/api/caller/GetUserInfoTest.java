package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.twitter.api.response.ResponseCore;
import com.github.graycat27.twitterbot.twitter.api.response.UserInfoData;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetUserInfoTest {

    @Test
    public void fromJson(){

        String input = "{\"data\":[{\"pinned_tweet_id\":\"1184079696876556288\",\"description\":\"灰猫\"," +
                "\"id\":\"466278557\"}]}";

        Gson gson = new Gson();
        ResponseCore data =  gson.fromJson(input, ResponseCore.class);

        System.out.println(data);

        assertAll(
                () -> assertEquals("466278557", data.getData().get("id")),
                () -> assertEquals("1184079696876556288", data.getData().get("pinned_tweet_id")),
                () -> assertEquals("灰猫", data.getData().get("description"))
        );
    }


}