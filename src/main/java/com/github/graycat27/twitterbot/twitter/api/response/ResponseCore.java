package com.github.graycat27.twitterbot.twitter.api.response;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Arrays;

public class ResponseCore {

    private LinkedTreeMap<String,String>[] data;
    public LinkedTreeMap<String,String> getData(){
        return data[0];
    }


    public String toString(){
        return "ResponseCore=data:{"+ Arrays.toString(data) +"}";
    }
}
