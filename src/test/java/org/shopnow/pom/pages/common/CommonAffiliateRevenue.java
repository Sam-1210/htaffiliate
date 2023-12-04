package org.shopnow.pom.pages.common;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.shopnow.base.BasePage;
import org.shopnow.enums.ExecutionType;
import org.shopnow.enums.PageType;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.structures.Page;
import org.shopnow.structures.Sitemap;
import org.shopnow.structures.TestData;
import org.shopnow.utility.DriverHelper;
import org.shopnow.utility.Logger;

import java.time.Duration;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CommonAffiliateRevenue extends BasePage {
    final ArrayList<String> SocialLinks = new ArrayList<>(List.of("instagram.",
            "whatsapp.", "twitter.", "x.",
            "facebook.", "youtube."));

    final ArrayList<String> PartnerLinks = new ArrayList<>(List.of("amazon.",
            "flipkart.", "croma."));

    final ArrayList<String> HTProducts = new ArrayList<>(List.of("hindustantimes.com",
            "livemint.com", "livehindustan.com"));

    private boolean RunForStoryPages(TestData testData, Predicate<Page> logic) {
        boolean isSanity = ApplicationProperties.getInstance().getExecutionType().equals(ExecutionType.SANITY);

        JSONArray testStories = new JSONArray();
        ;
        if (!isSanity) {
            testStories = Sitemap.GetAllStoriesInFeed();
        } else {
            Object data = (JSONArray) testData.GetData();
            if (data instanceof JSONArray)
                testStories = (JSONArray) data;
        }

        // temp
        JSONObject tempEntry = new JSONObject();
        tempEntry.put("page", "temp");
        JSONArray tmpArr = new JSONArray();
        tmpArr.put("http://ht-affiliate-preprod.hindustantimes.com/fashion/womens-wear/top-5-punjabi-salwar-suits-for-a-timeless-ethnic-look-201694152483343.html");
        tempEntry.put("stories", tmpArr);
        testStories.put(tempEntry);

        boolean result = true;

        try {
            for (Object obj : testStories) {
                JSONObject jObj = (JSONObject) obj;
                Logger.Heading((String) jObj.get("page"));
                for (Object url : jObj.getJSONArray("stories")) {
                    Logger.Log("Verifying on Story: %s", url.toString());
                    driver.get(url.toString());
                    result &= logic.test(new Page(url.toString(),  "", PageType.STORY));
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    private boolean RunForSectionPages(TestData testData, Predicate<Page> logic) {
        boolean isSanity = ApplicationProperties.getInstance().getExecutionType().equals(ExecutionType.SANITY);

        List<Map<String, String>> testSections = new ArrayList<>();
        if (!isSanity) {
            JSONObject sitemap = Sitemap.Get(false);
            testSections = Sitemap.GetFlattenedSitemap(sitemap, applicationProperties.getEnvironment());
        } else {
            Object data = (JSONArray) testData.GetData();
            JSONArray jdata = new JSONArray();
            if (data instanceof JSONArray)
                jdata = (JSONArray) data;

            for (Object testEntry : jdata) {
                JSONObject jTestEntry = (JSONObject) testEntry;
                Map<String, String> entry = new HashMap<>();
                try {
                    entry.put("title", jTestEntry.getString("title"));
                    entry.put("url", jTestEntry.getString("url"));
                    testSections.add(entry);
                } catch (Exception e) {
                    Logger.Except(e);
                }
            }
        }

        // temp
        /*Map<String, String> tEntry = new HashMap<>();
        tEntry.put("title", "electronics");
        tEntry.put("url", "https://shopnow.hindustantimes.com/electronics");
        testSections.add(new HashMap<>(tEntry));
        tEntry.put("title", "fashion");
        tEntry.put("url", "https://shopnow.hindustantimes.com/fashion");
        testSections.add(new HashMap<>(tEntry));*/

        boolean result = true;

        try {
            for (Map<String, String> section : testSections) {
                Logger.Log("Verifying on section/subsection: %s", section.get("url"));
                driver.get(section.get("url"));
                result &= logic.test(new Page(section.get("url"), "", PageType.SECTION));
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }

    // ########################## LOGIC LAMBDAS ###########################
    final private Predicate<Page> NoFollowLogic = (page) -> {
        boolean result = true;
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));

        for (WebElement aLink : allLinks) {
            String href = aLink.getAttribute("href");
            for (String scl : SocialLinks) {
                if (href != null && href.contains(scl)) {
                    String rel = aLink.getAttribute("rel");
                    if (rel == null || !rel.contains("nofollow")) {
                        Logger.Log("class%s, id%s",aLink.getAttribute("class"),aLink.getAttribute("id"));
                        Logger.Log("Link %s is not nofollow", href);
                        result = false;
                    }
                    break;
                }
            }

            for (String pcl : PartnerLinks) {
                if (href != null && href.contains(pcl)) {
                    String rel = aLink.getAttribute("rel");
                    if (rel == null || !rel.contains("nofollow")) {
                        Logger.Log("Link %s is not nofollow", href);
                        result = false;
                    }
                    break;
                }
            }
        }
        return result;
    };

    private final Predicate<Page> TargetAttributeLogic = (page) -> {
        boolean result = true;
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));

        for (WebElement aLink : allLinks) {
            String href = aLink.getAttribute("href");
            if (href == null) continue;
            boolean isHTProd = false;
            for (String htl : HTProducts) {
                if (href.contains(htl)) {
                    isHTProd = true;
                    break;
                }
            }
            if (!isHTProd && !href.contains("javascript:void(0)")) {
                String target = aLink.getAttribute("target");
                if (target == null || !target.contains("_blank")) {
                    Logger.Log("Link %s does not open on new tab", href);
                    result = false;
                }
            }
        }
        return result;
    };

    private final Predicate<Page> AmazonTagsLogic = (page) -> {
        boolean result = true;
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));

        for (WebElement aLink : allLinks) {
            String href = aLink.getAttribute("href");
            if (href == null) continue;

            if (href.contains("amazon.")) {
                if (!href.contains("&tag=ht-shopnow-")) {
                    Logger.Log("Tags not set for link: %s", href);
                    result = false;
                }
            }
        }
        return result;
    };

    private final Predicate<Page> FlipkartTagsLogic = (page) -> {
        boolean result = true;
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));

        for (WebElement aLink : allLinks) {
            String href = aLink.getAttribute("href");
            if (href == null) continue;

            if (href.contains("flipkart.")) {
                if (!href.contains("affid=htaffiliate")
                || !href.contains("affExtParam1=HTShopNow")) {
                    Logger.Log("Tags not set for link: %s", href);
                    result = false;
                }
                if(page.getType().equals(PageType.SECTION)) {
                    // TODO implement
                } else if (page.getType().equals(PageType.STORY)){
                    if (!href.contains("affExtParam2=listicle")) {
                        Logger.Log("Widget Name not set for link: %s", href);
                        result = false;
                    }
                }
            }
        }
        return result;
    };

    private final Predicate<Page> CromaTagsLogic = (page) -> {
        boolean result = true;
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));

        for (WebElement aLink : allLinks) {
            String href = aLink.getAttribute("href");
            if (href == null) continue;

            if (href.contains("flipkart.")) {
                if (!href.contains("ref_id=htaffiliate")
                        || !href.contains("sub1=HTShopNow")) {
                    Logger.Log("Tags not set for link: %s", href);
                    result = false;
                }
                if(page.getType().equals(PageType.SECTION)) {
                    // TODO implement
                } else if (page.getType().equals(PageType.STORY)){
                    if (!href.contains("sub2=listicle")) {
                        Logger.Log("Widget Name not set for link: %s", href);
                        result = false;
                    }
                }
            }
        }
        return result;
    };


    //########################### RUNNER FUNCTIONS #####################################
    public boolean checkNoFollowStoryPages(TestData testData) {
        return RunForStoryPages(testData, NoFollowLogic);
    }

    public boolean checkNoFollowSectionPages(TestData testData) {
        return RunForSectionPages(testData, NoFollowLogic);
    }

    public boolean checkTargetAttributeOnStories(TestData testData) {
        return RunForStoryPages(testData, TargetAttributeLogic);
    }

    public boolean checkTargetAttributeOnSectionPages(TestData testData) {
        return RunForSectionPages(testData, TargetAttributeLogic);
    }

    public boolean checkTagsAmazonOnStories(TestData testData) {
        return RunForStoryPages(testData, AmazonTagsLogic);
    }

    public boolean checkTagsAmazonOnSectionPages(TestData testData) {
        return RunForSectionPages(testData, AmazonTagsLogic);
    }

    public boolean checkTagsFlipkartOnStories(TestData testData) {
        return RunForStoryPages(testData, FlipkartTagsLogic);
    }

    public boolean checkTagsFlipkartOnSectionPages(TestData testData) {
        return RunForSectionPages(testData, FlipkartTagsLogic);
    }

    public boolean checkTagsCromaOnStories(TestData testData) {
        return RunForStoryPages(testData, FlipkartTagsLogic);
    }

    public boolean checkTagsCromaOnSectionPages(TestData testData) {
        return RunForSectionPages(testData, FlipkartTagsLogic);
    }
}