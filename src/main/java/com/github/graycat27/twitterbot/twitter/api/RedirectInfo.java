package com.github.graycat27.twitterbot.twitter.api;

import com.github.graycat27.twitterbot.utils.UrlString;

import java.util.UUID;

public class RedirectInfo {
    public final UUID state;
    public final UUID challenge;
    public final UrlString url;

    public RedirectInfo(UUID state, UUID challenge, UrlString url){
        this.state = state; this.challenge = challenge; this.url = url;
    }
}
