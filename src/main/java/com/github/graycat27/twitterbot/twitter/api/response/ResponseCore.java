package com.github.graycat27.twitterbot.twitter.api.response;

import com.github.graycat27.twitterbot.twitter.api.response.data.IMetaData;
import com.github.graycat27.twitterbot.utils.JsonUtil;

public class ResponseCore<T extends IMetaData> {

    private T data;
    public T getData(){
        return data;
    }

    public String toString(){
        return "ResponseCore:"+ JsonUtil.getJsonString(this);
    }
}
