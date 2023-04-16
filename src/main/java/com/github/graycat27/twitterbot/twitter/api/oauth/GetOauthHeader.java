package com.github.graycat27.twitterbot.twitter.api.oauth;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.response.data.OauthToken;
import com.github.graycat27.twitterbot.twitter.api.response.data.RequestToken;
import com.github.graycat27.twitterbot.utils.UrlString;
import org.apache.http.NameValuePair;
import org.springframework.http.HttpMethod;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class GetOauthHeader {

    private GetOauthHeader(){ /* インスタンス化防止 */ }

    public static String getOauthHeader(OauthToken token, UrlString url, HttpMethod method, List<NameValuePair> requestParam){
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        TwitterAuthDomain authInfo = authQuery.selectOne(null);

        RequestDomain oauthRequest = new RequestDomain(
                authInfo.getApiKey(), authInfo.getSecretKey(),
                token == null ? null : token.getToken(), token == null ? null : token.getTokenSecret(),
                method, url.url
        );

        SortedMap<String, String> authMap = getAuthMap(requestParam, oauthRequest, token);
        String signatureParam = createSign(authMap);

        String signatureBaseData = createBase(method, url, signatureParam);
        String signatureSecretKey = createKey(oauthRequest.getConsumerSecret(), oauthRequest.getOauthTokenSecret());
        String signature = calcSignature(signatureBaseData, signatureSecretKey);
        return createAuthHeader(url, signature, authMap);
    }

    private static String urlEncode(String string) {
        String encoded = URLEncoder.encode(string, StandardCharsets.UTF_8);
        return encoded.replaceAll("\\+","%20");
    }

    /**
     * ref https://www.delftstack.com/howto/java/random-alphanumeric-string-in-java/#generate-random-alphanumeric-string-in-java-using-the-math-random-method
     */
    private static String generateNonceData(){
        int maxLen = 42;
        StringBuilder sb = new StringBuilder(maxLen);
        String oneByteChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        for(int i=0; i<maxLen; i++){
            int randIdx = (int)(oneByteChar.length() * Math.random());
            sb.append(oneByteChar.charAt(randIdx));
        }
        return sb.toString();
    }

    private static SortedMap<String,String> getAuthMap(List<NameValuePair> queryParam, RequestDomain requestParam, OauthToken token){
        SortedMap<String, String> authMap = new TreeMap<>();

        if(queryParam != null) {
            for (NameValuePair pair : queryParam) {
                authMap.put(urlEncode(pair.getName()), urlEncode(pair.getValue()));
            }
        }
        authMap.put(urlEncode("oauth_consumer_key"), urlEncode(requestParam.getConsumerKey()));
        authMap.put(urlEncode("oauth_nonce"), urlEncode(generateNonceData()));
        authMap.put(urlEncode("oauth_signature_method"), urlEncode("HMAC-SHA1"));
        authMap.put(urlEncode("oauth_timestamp"), urlEncode(String.valueOf((int)(System.currentTimeMillis()/1000L))));
        authMap.put(urlEncode("oauth_version"), urlEncode("1.0"));

        if(token != null){
            authMap.put("oauth_token", urlEncode(token.getToken()));
            if(token instanceof RequestToken requestToken) {
                if (requestToken.getOauthVerifier() != null) {
                    authMap.put("oauth_verifier", urlEncode(requestToken.getOauthVerifier()));
                }
            }
        }

        return authMap;
    }

    private static String createSign(SortedMap<String,String> authParam){
        Iterator<Map.Entry<String, String>> ite = authParam.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (ite.hasNext()) {
            Map.Entry<String, String> entry = ite.next();
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            if(ite.hasNext()){
                sb.append('&');
            }
        }
        return sb.toString();
    }

    private static String createBase(HttpMethod method, UrlString url, String encodedParam){
        return method.name() + "&" + urlEncode(url.url) + "&" + urlEncode(encodedParam);
    }

    private static String createKey(String consumerSecret, String tokenSecret){
        return consumerSecret + "&" + ((tokenSecret != null) ? tokenSecret: "");
    }

    private static String calcSignature(String data, String key){
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance(signingKey.getAlgorithm());
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createAuthHeader(UrlString url, String signature, SortedMap<String,String> authMap){
        StringBuilder sb = new StringBuilder();

        authMap.put("oauth_signature", urlEncode(signature));

        sb.append("OAuth ");
        Iterator<Map.Entry<String, String>> ite = authMap.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry<String, String> entry = ite.next();
            sb.append(entry.getKey());
            sb.append('=');
            sb.append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if(ite.hasNext()){
                sb.append(',').append(' ');
            }
        }
        return sb.toString();
    }


}
