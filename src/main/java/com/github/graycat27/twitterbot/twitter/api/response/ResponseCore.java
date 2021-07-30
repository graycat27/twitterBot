package com.github.graycat27.twitterbot.twitter.api.response;

import java.util.Hashtable;

public class ResponseCore {

    public Hashtable<String, String> data;

    public String toString(){
        return "ResponseCore=data:{"+ data.toString() +"}";
    }
}
