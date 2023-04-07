package com.github.graycat27.twitterbot.utils;

import java.net.URI;

public class UrlString{
    public final String url;
    public UrlString(String url){
        this.url = url;
    }
    public UrlString(URI uri){
        this.url = uri.toString();
    }
    public String toString(){
        return url;
    }
}