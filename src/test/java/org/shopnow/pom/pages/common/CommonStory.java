package org.shopnow.pom.pages.common;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.java.Log;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.shopnow.base.BasePage;
import org.shopnow.structures.TestData;
import org.shopnow.utility.DriverHelper;
import org.shopnow.utility.DriverManager;
import org.shopnow.utility.Logger;
import org.shopnow.utility.NetworkUtils;


public class CommonStory extends BasePage {
    /**
     * naming conventions for locators
     * List* returns list
     * _* must be used relative to parent
     * el* defines the variable is a WebElement
     */


    /**
     * locators | common for all platforms
     */
    // absolute
    private static final By StoryTitle = By.cssSelector("div.container > h1.storyH1");
    private static final By PublishedOn = By.cssSelector("div.published > span.published_info");
    private static final By AuthorImage = By.cssSelector("div.published_by > span.published_by_img > img");
    private static final By AuthorName = By.cssSelector("div.published_by");
    private static final By FollowUsText = By.cssSelector("div.social");
    private static final By FollowUsIconContainer = By.cssSelector("div.social > ul.social_list");
    private static final By ListFollowUsIcons = By.cssSelector("div.social > ul.social_list > li");
    private static final By ShareIcon = By.cssSelector("ul.share_list > li > span.shareIconWrap");
    private static final By ShareMenu = By.cssSelector("ul.share_list > li > div.shareListBox");
    private static final By ShareMenuTitle = By.cssSelector("ul.share_list > li > div.shareListBox > p.title");
    private static final By ShareMenuIconContainer = By.cssSelector("ul.share_list > li > div.shareListBox > div.shareListWrap");
    private static final By ListShareMenuIcons= By.cssSelector("ul.share_list > li > div.shareListBox > div.shareListWrap > span");
    private static final By StoryBanner = By.cssSelector("div.home_banner img");
    private static final By SummaryTitle = By.cssSelector("div.summary p:first-child");
    private static final By SummaryPara = By.cssSelector("div.summary > p.summary-desc");
    private static final By SummaryReadMore = By.cssSelector("div.summary > p.summary-desc > a");
    private static final By SummaryReadLess = By.cssSelector("div.summary > a#readLessBtn");
    private static final By SummaryProductPriceTable = By.cssSelector("div.summary > div.productTableContainer > table");

    // relative



    /**
     * mandatory test | Platform Specifics
     * make abstract
     */

    /**
     * optional tests | Platform Specific
     * provide body returning true
     */

    /**
     * default, common tests for all
     */
    public boolean checkTitleVisibility(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            result = driver.findElement(StoryTitle).isDisplayed() && driver.findElement(StoryTitle).getTagName().equalsIgnoreCase("h1");
            if(!driver.findElement(StoryTitle).isDisplayed()) Logger.Log("Story Title is not visible");
            if(!driver.findElement(StoryTitle).getTagName().equalsIgnoreCase("h1")) Logger.Log("Story title is not h1");
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkStoryTitle(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            String storyTitle = driver.findElement(StoryTitle).getText();
            String pageTitle = driver.getTitle();
            result = pageTitle.equalsIgnoreCase(storyTitle + " | HT Shop Now");
            if(!result) Logger.Log("Mismatch ::: Page Title:%s | Story Title:%s", pageTitle, storyTitle);
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkPublishDate(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();
        String regex = "Published on (\\w{3}) (\\d{2}), (\\d{4}) (\\d{2}):(\\d{2}) IST";
        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            if(!driver.findElement(PublishedOn).isDisplayed()) {
                Logger.Log("Publish date is not visible");
                return false;
            }
            String pubText = driver.findElement(PublishedOn).getText();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(pubText);
            if(!matcher.matches()) {
                result = false;
                Logger.Log("Wrong Format of Publish Date::: '%s'", pubText);
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkAuthorImageLoaded(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, AuthorImage);
            DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofMillis(500), AuthorImage);
            int status = NetworkUtils.URLStatus(driver.findElement(AuthorImage).getAttribute("src"));
            if(status != 200) result = false;
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkAuthorName(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();
        String regex = "By: (.+)";
        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            if(!driver.findElement(AuthorName).isDisplayed()) {
                Logger.Log("Author name is not displayed");
                return false;
            }
            String authorName = driver.findElement(AuthorName).getText();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(authorName);
            if(!matcher.matches()) {
                result = false;
                Logger.Log("Wrong Format of Author Name::: '%s'", authorName);
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkFollowUsText(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            if(!driver.findElement(FollowUsText).isDisplayed()) {
                Logger.Log("Follow us text is not displayed");
                return false;
            }
            String followustext = driver.findElement(FollowUsText).getText();

            if(!followustext.trim().equalsIgnoreCase("follow us:")) {
                result = false;
                Logger.Log("Mismatch ::: Actual:'%s' | Expected: 'Follow Us:'", followustext);
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkSocialMediaCountInFollowUs(TestData testData) {
        boolean result = true;
        int expected = 5;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            result = driver.findElements(ListFollowUsIcons).size() == expected;
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkSocialMediaIconLoadedInFollowUs(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            List<WebElement> elsSocialIcons = driver.findElements(ListFollowUsIcons);
            DriverHelper.ScrollWithJS(driver, FollowUsIconContainer);

            for(WebElement elIcon:elsSocialIcons) {
                WebElement elImg = elIcon.findElement(By.tagName("img"));
                DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofMillis(500), elImg);
                int status = NetworkUtils.URLStatus(elImg.getAttribute("src"));
                if(status != 200) {
                    Logger.Log("Invalid Icon Resource: %s", elImg.getAttribute("src"));
                    result = false;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkXXXX(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));



        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }
    public boolean checkXXXXX(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));



        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkXXXXXX(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));



        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkXXXXXXX(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));



        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }
}
