package com.github.graycat27.twitterbot.twitter.api.response.data;

/**
 * アクセストークン取得時にのみ使用する使い捨てトークンデータ
 */
public class RequestToken extends IMetaData {

    private String oauth_token;
    private String oauth_token_secret;

}
