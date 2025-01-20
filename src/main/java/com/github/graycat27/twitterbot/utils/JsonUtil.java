package com.github.graycat27.twitterbot.utils;

import com.google.gson.*;
import org.apache.hc.core5.http.NameValuePair;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {

    static {
        GsonBuilder builder = new GsonBuilder();
       // builder.registerTypeAdapter(IXxx.class, new InterfaceAdapter());
        gson = builder.create();
    }

    private JsonUtil(){ /* インスタンス化防止 */ }

    public static final Gson gson;

    /** 引数のobjectをJSON文字列に変換します */
    public static String getJsonString(Object o){
        return gson.toJson(o);
    }
    public static String getJsonString(List<NameValuePair> nvp){
        StringBuilder sb = new StringBuilder("{");
        for(NameValuePair nv : nvp){
            sb.append(nv.getName()).append(":").append(nv.getValue());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }

    /** JSON文字列からType引数の型のオブジェクトに変換します */
    public static <T> T getObjectFromJsonStr(String json, Type o){
        return gson.fromJson(json, o);
    }
}

/* ref: https://github.com/graycat27/AirportTrafficController/blob/main/src/main/java/com/github/graycat27/atc/components/data/json/InterfaceAdapter.java */
/** インターフェースをJSONに出力できるようにするアダプター */
class InterfaceAdapter implements JsonSerializer, JsonDeserializer {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String DATA = "DATA";
    /*
        json format ::=
        "CLASSNAME":"pack.age.Class",
        "DATA": {
           "field1":"value1"
        }
     */

    public Object deserialize(JsonElement jsonElement, Type type,
                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class klass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
    }
    public JsonElement serialize(Object jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, jsonElement.getClass().getName());
        jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement));
        return jsonObject;
    }
    /****** Helper method to get the className of the object to be deserialized *****/
    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
    }
}
