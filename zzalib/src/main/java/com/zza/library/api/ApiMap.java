package com.zza.library.api;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/6/21.
 */

public class ApiMap {

    private static Map<String, Object> map = new HashMap<>();

    public static Map<String, Object> getMap() {
        map.clear();
        return map;
    }


    public static RequestBody getRequestBody(Map<String, Object> params) {
        if (map == null) {
            map = getMap();
        }
        map.putAll(params);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), new JSONObject(map).toString());
        return requestBody;
    }
}
