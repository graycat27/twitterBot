package com.github.graycat27.twitterbot.twitter.api.caller;

import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class RequestTokenGetterApiTest {

    /** テスト対象がprivate staticメソッドなので、リフレクションによる呼出しをする処理 */
    private RequestToken convertQueryStr2Domain(String inQuery){
        //RequestTokenGetterApi
        RequestToken result;
        try{
            Method method = RequestTokenGetterApi.class.getDeclaredMethod("convertQueryStr2Domain", String.class);
            method.setAccessible(true);
            result = (RequestToken)method.invoke(null, inQuery);  //staticなのでnull引数

        }catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    //@Test
    public void convQuery2Dom_normal(){
        String in = "oauth_token=token_val&oauth_token_secret=secretVal&oauth_callback_confirmed=true";
        RequestToken res = convertQueryStr2Domain(in);

        assertAll(
                () -> assertEquals("token_val", res.getToken()),
                () -> assertEquals("secretVal", res.getTokenSecret())
        );
    }
}