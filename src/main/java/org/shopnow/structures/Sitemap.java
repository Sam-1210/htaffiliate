package org.shopnow.structures;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.shopnow.enums.Environment;
import org.shopnow.utility.DriverManager;
import org.shopnow.utility.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sitemap {
    public static JSONObject Get(boolean nullOnResourceFailure) {
        return Get("Sitemap.json", 2023, nullOnResourceFailure);
    }

    public static JSONObject Get(String resource, int year, boolean nullOnResourceFailure) {
        JSONObject obj = null;
        boolean readSuccess = false;
        InputStream in = ClassLoader.getSystemResourceAsStream(resource);

        if (in != null) {
            try {
                String jsonString = new String(in.readAllBytes());
                obj = new JSONObject(jsonString);
                readSuccess = true;
            } catch (Exception e) {
                Logger.Except(e);
            }
        }

        if (!readSuccess && !nullOnResourceFailure) {
            Logger.Log("Resource Failure | Sitemap will be generated dynamically");
            obj = Get(DriverManager.getInstance().getDriver(), year);
        }

        return obj;
    }
    public static JSONObject Get(WebDriver driver, int year) {
        String backup = driver.getCurrentUrl();
        JSONObject dynSitemap = null;
        List<JSONObject> data = new ArrayList<>();

        try {
            driver.get("https://shopnow.hindustantimes.com/sitemap");
            List<WebElement> elms = driver.findElements(By.cssSelector("div.siteMap-section > ul"));

            for (WebElement elm : elms) {
                List<WebElement> subElms = elm.findElements(By.cssSelector("li.sitemapsublist a"));

                WebElement head = elm.findElement(By.cssSelector("li.header.sitemaplist a"));
                String href = head.getAttribute("href");
                String url = href.substring(href.lastIndexOf('/') + 1);

                ArrayList<JSONObject> subcategories = new ArrayList<>();
                for (WebElement celm : subElms) {
                    JSONObject citem = new JSONObject();
                    String curl = celm.getAttribute("href");

                    citem.put("title", celm.getText());
                    citem.put("url", curl.substring(curl.lastIndexOf('/') + 1));
                    subcategories.add(citem);
                }

                JSONObject item = new JSONObject();
                item.put("title", head.getText());
                item.put("url", url);
                item.put("subcategories", subcategories);
                if(!url.isEmpty()) item.put("stories", String.format("https://shopnow.hindustantimes.com/%s/sitemap/%d.xml", url, year));
                data.add(item);
            }

            dynSitemap = new JSONObject();
            dynSitemap.put("year", year);
            dynSitemap.put("data", data);
        } catch (Exception e) {
            Logger.Except(e);
        }
        finally {
            driver.get(backup);
        }

        return dynSitemap;
    }

    public static List<Map<String, String>> GetFlattenedSitemap(JSONObject sitemap, Environment env) {
        List<Map<String, String>> flattened = new ArrayList<>();
        String baseURL = env.getURL();

        for(Object item: sitemap.getJSONArray("data")) {
            JSONObject jItem = (JSONObject) item;
            String categoryURL = baseURL + "/" + jItem.getString("url");
            Map<String, String> entry = new HashMap<>();
            entry.put("url", categoryURL);
            entry.put("title", jItem.getString("title"));
            flattened.add(new HashMap<>(entry));
            entry.clear();
            for(Object sitem : jItem.getJSONArray("subcategories")) {
                JSONObject sJitem = (JSONObject) sitem;
                entry.put("url", categoryURL + "/" + sJitem.getString("url"));
                entry.put("title", sJitem.getString("title"));
                flattened.add(new HashMap<>(entry));
                entry.clear();
            }
        }

        return flattened;
    }
}
