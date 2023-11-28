package org.shopnow.structures;

import org.json.JSONObject;
import org.shopnow.utility.Logger;

public class TestData {
    private String rawData;
    private JSONObject parsedData;

    public TestData(String jsonStr) {
        try {
            if(jsonStr.isEmpty()) jsonStr = "{}";
            parsedData = new JSONObject(jsonStr);
        } catch (Exception e) {
            Logger.Except(e);
            parsedData = null;
        }
    }

    public JSONObject GetData() {
        if (parsedData != null) return parsedData;
        return null;
    }
}
