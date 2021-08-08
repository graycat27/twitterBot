package com.github.graycat27.twitterbot.utils;

public class TweetTemplate {

    private TweetTemplate(){ /* インスタンス化防止 */ }

    public static final String tag = " #ツイート数えったー ";

    public static final String hundred = "今日(%s)に入っておよそ%dツイートしました！" + tag;
    public static final String authed = "登録しました！毎日0時頃にツイート数をつぶやきます" + tag;
    public static final String minus = "（ツイ消し・RT先の消失等でマイナスになったようです）";
}
