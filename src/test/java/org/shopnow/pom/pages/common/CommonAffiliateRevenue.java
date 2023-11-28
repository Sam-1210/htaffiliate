package org.shopnow.pom.pages.common;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.shopnow.base.BasePage;
import org.shopnow.enums.ExecutionType;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.structures.Sitemap;
import org.shopnow.structures.TestData;
import org.shopnow.utility.Logger;

import java.util.*;

public class CommonAffiliateRevenue extends BasePage {
    public boolean checkNoFollow(TestData testData) {
        boolean isSanity = ApplicationProperties.getInstance().getExecutionType().equals(ExecutionType.SANITY);
        // if its sanity, run test on testdata, else all stories shown in feed for current date
        JSONArray testStories;
        if(!isSanity) {
            testStories = Sitemap.GetAllStoriesInFeed();
        } else {
            testStories = testData.GetData().getJSONArray("list");
        }

        ArrayList<String> SocialLinks = new ArrayList<>(List.of("https://www.instagram.com",
                "https://www.whatsapp.com", "https://twitter.com", "https://x.com/",
                "https://www.facebook.com/", "https://www.youtube.com/"));

        ArrayList<String> PartnerLinks = new ArrayList<>(List.of("https://www.amazon.",
                "https://www.flipkart./", "https://www.croma."));

        for(Object obj: testStories){
            JSONObject jObj = (JSONObject)obj;
            Logger.Heading((String)jObj.get("page"));
            for(Object url: jObj.getJSONArray("stories")) {
                Logger.Log("Verifying on Story: ");
                driver.get(url.toString());
                List<WebElement> allLinks = driver.findElements(By.tagName("a"));

                for(WebElement aLink: allLinks) {
                    String href = aLink.getAttribute("href");

                    for(String scl : SocialLinks)

                }
            }
        }

        return true;
    }

    public boolean checkNoFollowSectionPages() {
        return true;
    }
}
