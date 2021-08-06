package com.github.graycat27.twitterbot.twitter.api.oauth;

import com.github.graycat27.twitterbot.heroku.db.domain.TwitterAuthDomain;
import com.github.graycat27.twitterbot.heroku.db.query.TwitterAuthQuery;
import com.github.graycat27.twitterbot.twitter.api.ApiUrl;
import com.github.graycat27.twitterbot.twitter.api.response.data.OauthToken;
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

    public static String getOauthHeader(OauthToken token, ApiUrl.UrlString url, List<NameValuePair> requestParam){
        TwitterAuthQuery authQuery = new TwitterAuthQuery();
        TwitterAuthDomain authInfo = authQuery.selectOne(null);

        RequestDomain oauthRequest = new RequestDomain(
                authInfo.getApiKey(), authInfo.getSecretKey(),
                token == null ? null : token.getToken(), token == null ? null : token.getTokenSecret(),
                HttpMethod.POST, url.url
        );

        SortedMap<String, String> authMap = getAuthMap(requestParam, oauthRequest, token);
        String signatureParam = createSign(authMap);

        String signatureBaseData = createBase(HttpMethod.POST, url, signatureParam);
        String signatureSecretKey = createKey(oauthRequest.getConsumerSecret(), oauthRequest.getOauthTokenSecret());

        String signature = calcSignature(signatureBaseData, signatureSecretKey);

        String header = createAuthHeader(url, signature, authMap);
        System.out.println(header);
        return header;
    }

    private static String urlEncode(String string) {
        try {
            String encoded = URLEncoder.encode(string, StandardCharsets.UTF_8.name());
            return encoded.replaceAll("\\+","%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
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
            if(token instanceof RequestToken) {
                RequestToken requestToken = (RequestToken) token;
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

        System.out.println(sb);
        return sb.toString();
    }

    private static String createBase(HttpMethod method, ApiUrl.UrlString url, String encodedParam){
        String base = method.name() + "&" + urlEncode(url.url) + "&" + urlEncode(encodedParam);
        System.out.println(base);
        return base;
    }

    private static String createKey(String consumerSecret, String tokenSecret){
        return consumerSecret + "&" + tokenSecret;
    }

    private static String calcSignature(String data, String key){
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance(signingKey.getAlgorithm());
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            System.out.println(rawHmac);
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createAuthHeader(ApiUrl.UrlString url, String signature, SortedMap<String,String> authMap){
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

        System.out.println(sb);
        return sb.toString();
    }


}
