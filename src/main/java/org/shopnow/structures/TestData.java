package org.shopnow.structures;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.shopnow.utility.Logger;

public class TestData {
    private String rawData;
    private Object parsedData;

    private static Object parseResponse(String jsonResponse) {
        try {
            return new JSONObject(new JSONTokener(jsonResponse));
        } catch (org.json.JSONException e) {
            try {
                return new JSONArray(new JSONTokener(jsonResponse));
            } catch (org.json.JSONException ex) {
                System.out.println("Invalid JSON format");
                return null;
            }
        }
    }

    public TestData(String jsonStr) {
        try {
            if(jsonStr == null || jsonStr.isEmpty()) parsedData = null;
            else parsedData = parseResponse(jsonStr);
        } catch (Exception e) {
            Logger.Except(e);
            parsedData = null;
        }
    }

    public Object GetData() {
        if (parsedData != null) return parsedData;
        return null;
    }
}
