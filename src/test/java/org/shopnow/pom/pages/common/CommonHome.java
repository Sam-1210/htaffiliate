package org.shopnow.pom.pages.common;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.shopnow.base.BasePage;
import org.shopnow.enums.Environment;
import org.shopnow.enums.POM;
import org.shopnow.pom.components.common.CommonContextualWidget;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.utility.DriverHelper;
import org.shopnow.utility.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonHome extends BasePage {
    /**
     * naming conventions for locators
     * List* returns list
     * _* must be used relative to parent
     * el* defines the variable is a WebElement
     */

    @Getter
    protected final String URL = ApplicationProperties.getInstance().getEnvironment().getURL();

    /**
     * locators | common for all platforms
     */

    // absolute
    protected final By SwiperCardContainer = By.cssSelector("div.swiperbanner.swiper-container-horizontal > div.swiper-wrapper");
    protected final By ListSwiperCards = By.cssSelector("div.swiperbanner.swiper-container-horizontal > div.swiper-wrapper div.swiper-slide");
    protected final By MainContainer = By.cssSelector("div.productLeftSection");
    protected final By ListStoryCategoryContainers = By.cssSelector("div.listofproduct");
    protected final By ListCategoryNavigateButtons = By.cssSelector("div.productbtnbar > a");

    // relative
    protected final By _Link = By.tagName("a");
    protected final By _ListMainContainerChildren = By.cssSelector("div.productLeftSection > div, div.productLeftSection > section");
    protected final By _FirstStoryHeading = By.cssSelector("div.listProHeading.largeHeading > h1 > span > a");
    protected final By _OtherStoriesHeadings = By.cssSelector("div.listProHeading.largeHeading > h2 > span");
    protected final By _HighlightStory = By.cssSelector("section.productMainbox > div.productsCols:nth-child(1) > article.productFeatureBox");
    protected final By _HighlightTitle = By.cssSelector("div.featureTitle > a");
    protected final By _HighlightImg = By.cssSelector("div.proFeatureImg > img");
    protected final By _HighlightLink = By.cssSelector("div.featureTitle > a");
    protected final By _PublishDate = By.cssSelector("span.publishedText");
    protected final By _OtherStories = By.cssSelector("section.productMainbox > div.productsCols:nth-child(2) > article.proArticle");
    protected final By _OtherStoryTitle = By.cssSelector("div.proArticleWrap > a > div.proArticleRight > div.featureTitle2");
    protected final By _OtherStoryImg = By.cssSelector("div.artImgBox > img");
    protected final By _OtherStoryLink = By.cssSelector("div.proArticleWrap > a");

    /**
     * mandatory test | Platform Specifics
     * make abstract
     */

    /**
     * optional tests | Platform Specific
     * provide body returning true
     */
    public boolean checkBannerPaginationVisible() {
        return true;
    }

    public boolean checkBannerPaginationClickable() {
        return true;
    }

    /**
     * default, common tests for all
     */
    public boolean checkBannerVisibility() {
        boolean result = true;

        try {
            WebElement elBanner = driver.findElement(SwiperCardContainer);
            result = elBanner.isDisplayed();
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }

    public boolean checkBannerCardsCount() {
        boolean result = true;
        int expected = 14;

        try {
            List<WebElement> elBanners = driver.findElements(ListSwiperCards);
            result = elBanners.size() == expected;
            if(!result) Logger.Log("Banner card count mismatch | Expected:%d, Actual: %s", expected, elBanners.size());
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }

    public boolean checkBannerImages() {
        return true;
    }

    public boolean checkBannerCardsClick() {
        return true;
    }

    public boolean checkBannerCardsGA() {
        return true;
    }

    public boolean checkBannerCardsNoFollow() {
        boolean result = true;
        String expected = "sponsored";

        try {
            List<WebElement> elBanners = driver.findElements(ListSwiperCards);
            for(WebElement el : elBanners) {
                String actual = el.findElement(_Link).getAttribute("rel");
                boolean match = actual.equalsIgnoreCase(expected);
                if(!match) Logger.Log("Banner card rel mismatch for card num:%s | Expected:%s, Actual: %s",
                        el.getAttribute("data-vars-position"), expected, actual);
                result &= match;
            }

        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }

    public boolean checkNumberOfStoryCategories(int expected) {
        driver.get(URL);
        return driver.findElements(ListStoryCategoryContainers).size() == expected;
    }

    public boolean checkStoryCategoryTitles() {
        driver.get(URL);
        boolean result = true;
        String[] titles = {
                "BEST ONLINE DEALS AND OFFERS",
                "FASHION DEALS", "ELECTRONICS DEALS",
                "HEALTH AND BEAUTY DEALS", "PET CARE AND SUPPLIES DEALS",
                "HOME AND KITCHEN DEALS", "AMAZON SALE DEALS"
        };
        List<WebElement> elStoryCategoryContainer = driver.findElements(ListStoryCategoryContainers);
        String firstTitle = elStoryCategoryContainer.get(0).findElement(_FirstStoryHeading).getText();
        result &= firstTitle.equals(titles[0]);
        if(!result) {
            Logger.Trace("Heading Mismatch | Expected: %s, Actual: %s", titles[0], firstTitle);
        }
        for(int i = 1; i < elStoryCategoryContainer.size(); i++) {
            String actual = elStoryCategoryContainer.get(i).findElement(_OtherStoriesHeadings).getText();
            boolean match = actual.equals(titles[i]);
            if(!match) {
                Logger.Trace("Heading Mismatch | Expected: %s, Actual: %s", titles[i], actual);
            }
            result &= match;
        }

        return result;
    }

    public boolean checkCategoryOrder() {
        driver.get(URL);
        boolean result = true;
        String[] classes = {
                "listofproduct", "productbtnbar", "ofersliderbox"
        };
        List<WebElement> elStoryCategoryContainer = driver.findElement(MainContainer).findElements(_ListMainContainerChildren);
        boolean firstMatch = true;
        firstMatch = elStoryCategoryContainer.get(0).getAttribute("class").equals(classes[0]);
        if(!firstMatch) Logger.Log("Order Mismatch for category 1 | Expected div.%s, Actual: %s.%s",
                classes[0], elStoryCategoryContainer.get(0).getTagName(),
                elStoryCategoryContainer.get(0).getAttribute("class"));
        result &= firstMatch;
        firstMatch = elStoryCategoryContainer.get(1).getAttribute("class").equals(classes[1]);
        if(!firstMatch) Logger.Log("Order Mismatch for category 1 | Expected div.%s, Actual: %s.%s",
                classes[1], elStoryCategoryContainer.get(1).getTagName(),
                elStoryCategoryContainer.get(1).getAttribute("class"));
        result &= firstMatch;
        firstMatch = elStoryCategoryContainer.get(2).getAttribute("class").equals(classes[2]);
        if(!firstMatch) Logger.Log("Order Mismatch for category 1 | Expected section.%s, Actual: %s.%s",
                classes[2], elStoryCategoryContainer.get(2).getTagName(),
                elStoryCategoryContainer.get(2).getAttribute("class"));
        result &= firstMatch;

        int i = 3;
        for(; i < elStoryCategoryContainer.size(); i+=3) {
            boolean match = true;
            match = elStoryCategoryContainer.get(i).getAttribute("class").equals(classes[0]);
            if(!match) Logger.Log("Order Mismatch for category %d | Expected div.%s, Actual: %s.%s",
                    (i/3)+1, classes[0], elStoryCategoryContainer.get(i).getTagName(),
                    elStoryCategoryContainer.get(i).getAttribute("class"));
            result &= match;
            match = elStoryCategoryContainer.get(i+1).getAttribute("class").equals(classes[1]);
            if(!match) Logger.Log("Order Mismatch for category %d | Expected div.%s, Actual: %s.%s",
                    (i/3)+1, classes[1], elStoryCategoryContainer.get(i+1).getTagName(),
                    elStoryCategoryContainer.get(i+1).getAttribute("class"));
            result &= match;
            match = elStoryCategoryContainer.get(i+2).getAttribute("class").equals(classes[2]);
            if(!match) Logger.Log("Order Mismatch for category %d | Expected section.%s, Actual: %s.%s",
                    (i/3)+1, classes[2], elStoryCategoryContainer.get(i+2).getTagName(),
                    elStoryCategoryContainer.get(i+2).getAttribute("class"));
            result &= match;
        }

        if(i != elStoryCategoryContainer.size()) {
            Logger.Log("Some containers are missing");
        }

        return result;
    }

    public boolean checkNumberOfHighlightStoryInCategories() {
        driver.get(URL);
        boolean result = true;
        int expected = 1;
        List<WebElement> elStoryCategoryContainer = driver.findElements(ListStoryCategoryContainers);
        int firstCount = elStoryCategoryContainer.get(0).findElements(_HighlightStory).size();
        result &= firstCount == expected;
        if(!result) Logger.Trace("Highlight Story Number Mismatch | category [%s] | Expected: %s, Actual: %s",
                elStoryCategoryContainer.get(0).findElement(_FirstStoryHeading).getText(),
                expected, firstCount);
        for(int i = 1; i < elStoryCategoryContainer.size(); i++) {
            int highlightStoryCount = elStoryCategoryContainer.get(i).findElements(_HighlightStory).size();
            boolean match = highlightStoryCount == expected;
            if(!match) Logger.Trace("Highlight Story Number Mismatch | category [%s] | Expected: %s, Actual: %s",
                    elStoryCategoryContainer.get(i).findElement(_OtherStoriesHeadings).getText(),
                    expected, highlightStoryCount);
            result &= match;
        }

        return result;
    }

    public boolean checkNumberOfOtherStoriesInCategories() {
        driver.get(URL);
        boolean result = true;
        int expected = 3;
        List<WebElement> elStoryCategoryContainer = driver.findElements(ListStoryCategoryContainers);
        int firstCount = elStoryCategoryContainer.get(0).findElements(_OtherStories).size();
        result &= firstCount == expected;
        if(!result) Logger.Trace("Story Count Mismatch | category [%s] | Expected: %s, Actual: %s",
                elStoryCategoryContainer.get(0).findElement(_FirstStoryHeading).getText(),
                expected, firstCount);
        for(int i = 1; i < elStoryCategoryContainer.size(); i++) {
            int highlightStoryCount = elStoryCategoryContainer.get(i).findElements(_OtherStories).size();
            boolean match = highlightStoryCount == expected;
            if(!match) Logger.Trace("Story Count Mismatch | category [%s] | Expected: %s, Actual: %s",
                    elStoryCategoryContainer.get(i).findElement(_OtherStoriesHeadings).getText(),
                    expected, highlightStoryCount);
            result &= match;
        }

        return result;
    }

    protected Map<String, String> GetStoryHeadDetails(String StoryURL) {
        Map<String, String> details = new HashMap<>();
        String backup = driver.getCurrentUrl();

        try {
            driver.get(StoryURL);
            // TODO access from storyPage locator of these
            details.put("title", driver.findElement(By.cssSelector("div.proDetailHeading > h1")).getText());
            details.put("date", driver.findElement(By.cssSelector("li.byulPublished > span.abouttxt:nth-child(2)")).getText());
            //details.put("img", driver.findElement(By.cssSelector("div.pinfoPic img")).getAttribute("src"));
            details.put("link", StoryURL);

        } catch (Exception e) {
            Logger.Except(e);
        }

        driver.get(backup);
        return  details;
    }

    public boolean checkHighlightStories() {
        driver.get(URL);
        boolean result = true;
        List<WebElement> elStoryCategoryContainer = driver.findElements(ListStoryCategoryContainers);
        List<Map<String, String>> Actual = new ArrayList<>();

        for (WebElement webElement : elStoryCategoryContainer) {
            WebElement Highlight = webElement.findElement(_HighlightStory);
            Map<String, String> actual = new HashMap<>();
            // scroll wait for visibility
            //DriverHelper.ScrollWithJS(driver, Highlight);
            //DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofSeconds(3), Highlight.findElement(_HighlightImg));
            actual.put("title", Highlight.findElement(_HighlightTitle).getText());
            actual.put("date", Highlight.findElement(_PublishDate).getText());
            //actual.put("img", Highlight.findElement(_HighlightImg).getAttribute("src"));
            actual.put("link", Highlight.findElement(_HighlightLink).getAttribute("href"));
            Actual.add(new HashMap<>(actual));
        }

        for (Map<String, String> actual : Actual) {
            Map<String, String> expected = GetStoryHeadDetails(actual.get("link"));
            boolean match = expected.equals(actual);

            if (!match) Logger.Trace("Highlight Story Mismatch | Expected: %s, Actual: %s",
                    expected.toString(), actual.toString());
            result &= match;
        }

        return result;
    }

    public boolean checkOtherStories() {
        driver.get(URL);
        boolean result = true;
        List<WebElement> elStoryCategoryContainer = driver.findElements(ListStoryCategoryContainers);
        List<Map<String, String>> Actual = new ArrayList<>();

        for (WebElement webElement : elStoryCategoryContainer) {
            WebElement Highlight = webElement.findElement(_OtherStories);
            Map<String, String> actual = new HashMap<>();
            // scroll wait for visibility
            actual.put("title", Highlight.findElement(_OtherStoryTitle).getText());
            actual.put("date", Highlight.findElement(_PublishDate).getText());
            //actual.put("img", Highlight.findElement(_HighlightImg).getAttribute("src"));
            actual.put("link", Highlight.findElement(_OtherStoryLink).getAttribute("href"));
            Actual.add(new HashMap<>(actual));
        }

        for (Map<String, String> actual : Actual) {
            Map<String, String> expected = GetStoryHeadDetails(actual.get("link"));
            boolean match = expected.equals(actual);

            if (!match) Logger.Trace("Story Mismatch | Expected: %s, Actual: %s",
                    expected.toString(), actual.toString());
            result &= match;
        }

        return result;
    }

    public boolean checkCategoryNavigateButtons() {
        driver.get(URL);
        boolean result = true;
        String[] urls = {
                "fashion", "electronics", "health-and-beauty",
                "pet-supplies", "home-kitchen", "amazon-sale"
        };

        List<WebElement> elsNavigateButtons = driver.findElements(ListCategoryNavigateButtons);
        Logger.Log("Checking URLS");
        for(int i = 0; i < elsNavigateButtons.size(); i++) {
            String actual = elsNavigateButtons.get(i).getAttribute("href");
            actual = actual.substring(actual.lastIndexOf('/') + 1);
            boolean match = actual.equals(urls[i]);
            if(!match) {
                Logger.Trace("URL Mismatch | Expected: %s, Actual: %s", urls[i], actual);
            }
            result &= match;
        }

        Logger.Log("Checking Clicks");
        for(int i = 0; i < elsNavigateButtons.size(); i++) {
            String actual = "";

            tabHelper.OpenLinkInNewTab(elsNavigateButtons.get(i));
            {
                DriverHelper.ExplicitWaitForPageLoad(driver, Duration.ofSeconds(3));
                actual = driver.getCurrentUrl();
                actual = actual.substring(actual.lastIndexOf('/') + 1);
            }
            tabHelper.close();

            boolean match = actual.equals(urls[i]);

            if(!match) {
                Logger.Trace("Invalid Tab | Expected: %s, Actual: %s", urls[i], actual);
            }

            result &= match;
        }

        return result;
    }

    public boolean checkContextualWidgets() {
        boolean result = true;
        String[] categories = {
                "", "fashion", "electronics"
        };

        for(int i = 0; i < categories.length; i++) {
            result &= ((CommonContextualWidget)BasePage
                    .getInstanceOf(CommonContextualWidget.class, POM.COMPONENTS,
                            i, categories[i]))
                    .runAll();
        }

        return result;
    }
}
