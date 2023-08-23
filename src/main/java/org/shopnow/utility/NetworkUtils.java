package org.shopnow.utility;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class NetworkUtils {

    public static Map<String, String> parseGETQuery(String url) {
        Map<String, String> params = new LinkedHashMap<>();
        int questionMarkIndex = url.indexOf('?');
        if (questionMarkIndex >= 0) {
            String queryString = url.substring(questionMarkIndex + 1);
            String[] keyValuePairs = queryString.split("&");
            for (String pair : keyValuePairs) {
                int equalsIndex = pair.indexOf('=');
                if (equalsIndex >= 0) {
                    String key = pair.substring(0, equalsIndex);
                    String value = pair.substring(equalsIndex + 1);
                    key = URLDecoder.decode(key, StandardCharsets.UTF_8);
                    value = URLDecoder.decode(value, StandardCharsets.UTF_8);
                    params.put(key, value);
                }
            }
        }
        return params;
    }
}
