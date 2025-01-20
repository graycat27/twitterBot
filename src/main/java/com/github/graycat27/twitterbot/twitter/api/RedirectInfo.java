package com.github.graycat27.twitterbot.twitter.api;

import com.github.graycat27.twitterbot.utils.UrlString;

import java.util.UUID;

public class RedirectInfo {
    public final UUID state;
    public final String challenge;
    public final UrlString url;

    public RedirectInfo(UUID state, String challenge, UrlString url){
        this.state = state; this.challenge = challenge; this.url = url;
    }
}
