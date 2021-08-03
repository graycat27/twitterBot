package com.github.graycat27.twitterbot.twitter.api.response;

import com.github.graycat27.twitterbot.utils.JsonUtil;

import java.lang.reflect.Field;

public class IMetaData {

    public Object get(String key){
        try {
            Field field = this.getClass().getDeclaredField(key);
            field.setAccessible(true);
            return field.get(this);
        } catch (NoSuchFieldException|IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return JsonUtil.getJsonString(this);
    }
}
