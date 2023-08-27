package org.shopnow.pom.pages.web;

import com.beust.ah.A;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.shopnow.pom.pages.common.CommonHome;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.utility.DriverHelper;
import org.shopnow.utility.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends CommonHome {
    /**
     * naming conventions for locators
     * List* returns list
     * _* must be used relative to parent
     * el* defines the variable is a WebElement
     */

    // absolute
    private final By SwiperCardContainer = By.cssSelector("div.swiperbanner.swiper-container-horizontal > div.swiper-wrapper");
    private final By ListSwiperCards = By.cssSelector("div.swiperbanner.swiper-container-horizontal > div.swiper-wrapper div.swiper-slide");
    private final By SwiperPagination = By.cssSelector("div.swiperbanner.swiper-container-horizontal > div.swiper-pagination.swiper-pagination-clickable.swiper-pagination-bullets span");
    private final By MainContainer = By.cssSelector("div.productLeftSection");
    private final By ListStoryCategoryContainers = By.cssSelector("div.listofproduct");
    private final By ListCategoryNavigateButtons = By.cssSelector("div.productbtnbar > a");
    private final By ListCategoryContextualWidgets = By.cssSelector("section.ofersliderbox > div.offerSlideRow");

    // relative
    private final By _FirstStoryHeading = By.cssSelector("div.listProHeading.largeHeading > h1 > span > a");
    private final By _OtherStoriesHeadings = By.cssSelector("div.listProHeading.largeHeading > h2 > span > a");
    private final By _HighlightStory = By.cssSelector("section.productMainbox > div.productsCols:nth-child(1) > article.productFeatureBox");
    private final By _HighlightTitle = By.cssSelector("div.featureTitle > a");
    private final By _HighlightImg = By.cssSelector("div.proFeatureImg > img");
    private final By _HighlightLink = By.cssSelector("div.featureTitle > a");
    private final By _PublishDate = By.cssSelector("span.publishedText");
    private final By _OtherStories = By.cssSelector("section.productMainbox > div.productsCols:nth-child(2) > article.proArticle");
    private final By _OtherStoryTitle = By.cssSelector("div.proArticleWrap > a > div.proArticleRight > div.featureTitle2");
    private final By _OtherStoryImg = By.cssSelector("div.artImgBox > img");
    private final By _OtherStoryLink = By.cssSelector("div.proArticleWrap > a");

    @Override
    public boolean checkNumberOfStoryCategories() {
        driver.get(URL);
        return driver.findElements(ListStoryCategoryContainers).size() == 7;
    }

    @Override
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

    @Override
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

    @Override
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

    private Map<String, String> GetStoryHeadDetails(String StoryURL) {
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

    @Override
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

    @Override
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

    @Override
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

        String currentTab = driver.getWindowHandle();
        Logger.Log("Checking Clicks");
        for(int i = 0; i < elsNavigateButtons.size(); i++) {
            DriverHelper.OpenLinkInNewTab(elsNavigateButtons.get(i));
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            String actual = "";

            for (String tab : tabs) {
                if (!tab.equals(currentTab)) {
                    driver.switchTo().window(tab);
                    //DriverHelper.ExplicitWaitForPageLoad(driver, Duration.ofSeconds(2));
                    DriverHelper.ExplicitWaitForURL(driver, Duration.ofSeconds(2), URL + "/" + urls[i]);
                    actual = driver.getCurrentUrl();
                    driver.close();
                    break;
                }
            }
            actual = actual.substring(actual.lastIndexOf('/') + 1);
            driver.switchTo().window(currentTab);
            boolean match = actual.equals(urls[i]);

            if(!match) {
                Logger.Trace("Invalid Tab | Expected: %s, Actual: %s", urls[i], actual);
            }

            result &= match;
        }

        return result;
    }
}
