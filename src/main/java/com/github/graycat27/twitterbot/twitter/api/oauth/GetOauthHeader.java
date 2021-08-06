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

        SortedMap<String, String> oauthParam = new TreeMap<>();
        oauthParam.put("oauth_consumer_key", oauthRequest.getConsumerKey());
        oauthParam.put("oauth_signature_method", "HMAC-SHA1");
        oauthParam.put("oauth_timestamp", String.valueOf( (int)(System.currentTimeMillis()/1000L) ));
        oauthParam.put("oauth_nonce", generateNonceData());
        oauthParam.put("oauth_version", "1.0");

        oauthParam.put("oauth_token", oauthRequest.getOauthToken());
        if(token instanceof RequestToken) {
            RequestToken requestToken = (RequestToken) token;
            if (requestToken.getOauthVerifier() != null) {
                oauthParam.put("oauth_verifier", requestToken.getOauthVerifier());
            }
        }

        if(requestParam != null) {
            for (NameValuePair pair : requestParam) {
                oauthParam.put(pair.getName(), pair.getValue());
            }
        }

        // 署名(oauth_signature) の生成
        try{
            StringBuilder paramStrBuilder = new StringBuilder();
            for(Map.Entry<String, String> param : oauthParam.entrySet()){
                paramStrBuilder.append("&").append(param.getKey()).append("=").append(param.getValue());
            }
            String paramStr = paramStrBuilder.substring(1);   //最初の&を削除

            String baseText = oauthRequest.getMethod().name()
                    + "&" + urlEncode(oauthRequest.getUrlStr())
                    + "&" + urlEncode(paramStr);

            String signKey = urlEncode(oauthRequest.getConsumerSecret()) + "&"
                    + ((oauthRequest.getOauthTokenSecret() == null) ? "" : urlEncode(oauthRequest.getOauthTokenSecret()));

            SecretKeySpec signingKey = new SecretKeySpec(signKey.getBytes(), "HmacSHA1");
            javax.crypto.Mac mac = Mac.getInstance(signingKey.getAlgorithm());
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(baseText.getBytes());
            String signature = Base64.getEncoder().encodeToString(rawHmac);

            oauthParam.put("oauth_signature", urlEncode(signature));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Authorization header の作成
        StringBuilder paramStrBuilder = new StringBuilder();
        for(Map.Entry<String, String> param : oauthParam.entrySet()){
            if(param.getValue() != null) {
                paramStrBuilder.append(", ");
                paramStrBuilder.append(param.getKey()).append("=\"").append(urlEncode(param.getValue())).append("\"");
            }
        }
        String paramStr = paramStrBuilder.substring(2);
        String authHeader = "OAuth " + paramStr;

        System.out.println("generated oauth header = " + authHeader);
        return authHeader;
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
}
