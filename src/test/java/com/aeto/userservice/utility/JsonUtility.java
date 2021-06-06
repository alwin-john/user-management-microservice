package com.aeto.userservice.utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtility {

    public static String writeObjectAsString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public static JSONObject getUserInfo(String s) throws JSONException {
        JSONObject jsonObj = new JSONObject(s);
        return new JSONObject(jsonObj.getString("data"));
    }
}
