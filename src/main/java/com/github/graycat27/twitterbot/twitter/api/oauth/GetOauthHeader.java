package com.github.graycat27.twitterbot.twitter.api.oauth;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.data.AccessToken;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import org.apache.http.NameValuePair;
import org.springframework.http.HttpMethod;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class GetOauthHeader {

    private GetOauthHeader(){ /* インスタンス化防止 */ }

    public static String getOauthHeader(RequestToken token){
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        TwitterAuthDomain authInfo = authQuery.selectOne(null);

        RequestDomain oauthRequest = new RequestDomain(
                authInfo.getApiKey(), authInfo.getSecretKey(),
                "", "", HttpMethod.POST,
                ApiUrl.getRequestToken.url
        );

        SortedMap<String, String> oauthParam = new TreeMap<>();
        oauthParam.put("oauth_consumer_key", oauthRequest.getConsumerKey());
        oauthParam.put("oauth_signature_method", "HMAC-SHA1");
        oauthParam.put("oauth_timestamp", String.valueOf( (int)(System.currentTimeMillis()/1000L) ));
        oauthParam.put("oauth_nonce", get32ByteRandomData());
        oauthParam.put("oauth_version", "1.0");

        if(token != null){
            oauthParam.put("oauth_token", token.getToken());
        /*    if(token.getTokenSecret() != null){
                oauthParam.put("oauth_secret", token.getTokenSecret());
            }
            */
            if(token.getOauthVerifier() != null) {
                oauthParam.put("oauth_verifier", token.getOauthVerifier());
            }
        }

        // 署名(oauth_signature) の生成
        try{
            StringBuilder paramStrBuilder = new StringBuilder();
            for(Map.Entry<String, String> param : oauthParam.entrySet()){
                paramStrBuilder.append("&").append(param.getKey()).append("=").append(param.getValue());
            }
            String paramStr = paramStrBuilder.substring(1);   //最初の&を削除

            String baseText = oauthRequest.getMethod().toString()
                    + "&" + urlEncode(oauthRequest.getUrlStr())
                    + "&" + urlEncode(paramStr);

            String signKey = urlEncode(oauthRequest.getConsumerSecret())
                    + "&" + urlEncode(oauthRequest.getOauthTokenSecret());

            SecretKeySpec signingKey = new SecretKeySpec(signKey.getBytes(), "HmacSHA1");
            javax.crypto.Mac mac = Mac.getInstance(signingKey.getAlgorithm());
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(baseText.getBytes());
            String signature = Base64.getEncoder().encodeToString(rawHmac);

            oauthParam.put("oauth_signature", signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Authorization header の作成
        StringBuilder paramStrBuilder = new StringBuilder();
        for(Map.Entry<String, String> param : oauthParam.entrySet()){
            paramStrBuilder.append(", ")
                    .append(param.getKey()).append("=\"").append(urlEncode(param.getValue())).append("\"");
        }
        String paramStr = paramStrBuilder.substring(2);
        String authHeader = "OAuth " + paramStr;

        System.out.println("generated oauth header = " + authHeader);
        return authHeader;
    }

    public static String getUserOauthHeader(AccessToken token, ApiUrl.UrlString url, List<NameValuePair> requestParam){
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        TwitterAuthDomain authInfo = authQuery.selectOne(null);

        RequestDomain oauthRequest = new RequestDomain(
                authInfo.getApiKey(), authInfo.getSecretKey(),
                token.getToken(), token.getTokenSecret(), HttpMethod.POST,
                url.url
        );

        SortedMap<String, String> oauthParam = new TreeMap<>();
        oauthParam.put("oauth_consumer_key", oauthRequest.getConsumerKey());
        oauthParam.put("oauth_signature_method", "HMAC-SHA1");
        oauthParam.put("oauth_timestamp", String.valueOf( (int)(System.currentTimeMillis()/1000L) ));
        oauthParam.put("oauth_nonce", get32ByteRandomData());
        oauthParam.put("oauth_version", "1.0");
        oauthParam.put("oauth_token", oauthRequest.getOauthToken());

        for(NameValuePair pair : requestParam){
            oauthParam.put(pair.getName(), pair.getValue());
        }

        // 署名(oauth_signature) の生成
        try{
            StringBuilder paramStrBuilder = new StringBuilder();
            for(Map.Entry<String, String> param : oauthParam.entrySet()){
                paramStrBuilder.append("&").append(param.getKey()).append("=").append(param.getValue());
            }
            String paramStr = paramStrBuilder.substring(1);   //最初の&を削除

            String baseText = oauthRequest.getMethod().toString()
                    + "&" + urlEncode(oauthRequest.getUrlStr())
                    + "&" + urlEncode(paramStr);

            String signKey = urlEncode(oauthRequest.getConsumerSecret())
                    + "&" + urlEncode(oauthRequest.getOauthTokenSecret());

            SecretKeySpec signingKey = new SecretKeySpec(signKey.getBytes(), "HmacSHA1");
            javax.crypto.Mac mac = Mac.getInstance(signingKey.getAlgorithm());
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(baseText.getBytes());
            String signature = Base64.getEncoder().encodeToString(rawHmac);

            oauthParam.put("oauth_signature", signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        for(NameValuePair pair : requestParam){
            oauthParam.remove(pair.getName());
        }

        // Authorization header の作成
        StringBuilder paramStrBuilder = new StringBuilder();
        for(Map.Entry<String, String> param : oauthParam.entrySet()){
            paramStrBuilder.append(", ")
                    .append(param.getKey()).append("=\"").append(urlEncode(param.getValue())).append("\"");
        }
        String paramStr = paramStrBuilder.substring(2);
        String authHeader = "OAuth " + paramStr;

        System.out.println("generated oauth header = " + authHeader);
        return authHeader;
    }


    private static String urlEncode(String string) {
        try {
            return URLEncoder.encode(string, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ref https://www.delftstack.com/howto/java/random-alphanumeric-string-in-java/#generate-random-alphanumeric-string-in-java-using-the-math-random-method
     */
    private static String get32ByteRandomData(){
        int maxLen = 42;
        StringBuilder sb = new StringBuilder(maxLen);
        String oneByteChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        for(int i=0; i<maxLen; i++){
            int randIdx = (int)(oneByteChar.length() * Math.random());
            sb.append(oneByteChar.charAt(randIdx));
        }
        return sb.toString();
    }
}
