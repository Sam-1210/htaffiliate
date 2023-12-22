package org.shopnow.pom.pages.common;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.java.Log;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    private static final By ShareIcon = By.cssSelector("ul.share_list > li > span.shareIconWrap img");
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
            if(!driver.findElement(StoryTitle).isDisplayed()) Logger.Error("Story Title is not visible");
            if(!driver.findElement(StoryTitle).getTagName().equalsIgnoreCase("h1")) Logger.Error("Story title is not h1");
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
            if(!result) Logger.Error("Mismatch ::: Page Title:%s | Story Title:%s", pageTitle, storyTitle);
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
                Logger.Error("Publish date is not visible");
                return false;
            }
            String pubText = driver.findElement(PublishedOn).getText();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(pubText);
            if(!matcher.matches()) {
                result = false;
                Logger.Error("Wrong Format of Publish Date::: '%s'", pubText);
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
                Logger.Error("Author name is not displayed");
                return false;
            }
            String authorName = driver.findElement(AuthorName).getText();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(authorName);
            if(!matcher.matches()) {
                result = false;
                Logger.Error("Wrong Format of Author Name::: '%s'", authorName);
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
                Logger.Error("Follow us text is not displayed");
                return false;
            }
            String followustext = driver.findElement(FollowUsText).getText();

            if(!followustext.trim().equalsIgnoreCase("follow us:")) {
                result = false;
                Logger.Error("Mismatch ::: Actual:'%s' | Expected: 'Follow Us:'", followustext);
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
                    Logger.Error("Invalid Icon Resource: %s", elImg.getAttribute("src"));
                    result = false;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkSocialMediaURLs(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            List<WebElement> elsSocialMedias = driver.findElements(ListFollowUsIcons);
            for(WebElement aIcon: elsSocialMedias) {
                String href = aIcon.findElement(By.tagName("a")).getAttribute("href");
                String socialName = aIcon.findElement(By.tagName("img")).getAttribute("alt");
                int status = NetworkUtils.URLStatus(href);
                if(status != 200 && status != 301) {
                    result = false;
                    Logger.Error("Invalid URL: %s for Social Media: %s", href, socialName);
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkSocialMediaAlt(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            String[] allAlts = {"instagram", "whatsapp", "facebook", "twitter", "youtube"};
            List<WebElement> elsSocialMedias = driver.findElements(ListFollowUsIcons);
            for(int i = 0; i < elsSocialMedias.size(); i++) {
                String alt = elsSocialMedias.get(i).findElement(By.tagName("img")).getAttribute("alt");
                if(!alt.equalsIgnoreCase(allAlts[i])) {
                    Logger.Error("Alt not matches ::: Expected: %s | Actual: %s", allAlts[i], alt);
                    result = false;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkShareIconLoaded(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ShareIcon);
            DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofMillis(500), ShareIcon);
            int status = NetworkUtils.URLStatus(driver.findElement(ShareIcon).getAttribute("src"));
            if(status != 200) result = false;
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkShareMenuOpenClose(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ShareIcon);
            DriverHelper.ClickWithJS(driver, ShareIcon);
            try{
                DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofMillis(500), ShareMenu);
            } catch (Exception e) {
                Logger.Error("Share Menu failed to open");
                result = false;
                throw e;
            }

            DriverHelper.ClickWithJS(driver, ShareIcon);
            try{
                DriverHelper.ExplicitWaitForCondition(driver, Duration.ofMillis(500), ExpectedConditions.invisibilityOfElementLocated(ShareMenu));
            } catch (Exception e) {
                Logger.Error("Share Menu failed to close");
                result = false;
                throw e;
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkShareViaText(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ShareIcon);
            DriverHelper.ClickWithJS(driver, ShareIcon);
            DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofMillis(500), ShareMenu);

            if(!driver.findElement(ShareMenuTitle).isDisplayed()) {
                Logger.Error("Share via text is not displayed");
                return false;
            }
            String sharevia = driver.findElement(ShareMenuTitle).getText();

            if(!sharevia.trim().equalsIgnoreCase("Share via")) {
                result = false;
                Logger.Error("Mismatch ::: Actual:'%s' | Expected: 'Follow Us:'", sharevia);
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkShareOptionCount(TestData testData) {
        boolean result = true;
        int expected = 5;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ShareIcon);
            DriverHelper.ClickWithJS(driver, ShareIcon);
            DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofMillis(500), ShareMenu);

            result = driver.findElements(ListShareMenuIcons).size() == expected;
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkShareOptionsIconLoaded(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ShareIcon);
            DriverHelper.ClickWithJS(driver, ShareIcon);
            DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofMillis(500), ShareMenu);

            List<WebElement> elsShareOptions = driver.findElements(ListShareMenuIcons);

            for(WebElement elIcon:elsShareOptions) {
                WebElement elImg = elIcon.findElement(By.tagName("img"));
                DriverHelper.ScrollWithJS(driver, elIcon);
                Thread.sleep(100);
                String imgSrc = elImg.getAttribute("src");
                String optionName = elImg.getAttribute("alt");
                int status = NetworkUtils.URLStatus(imgSrc);
                if(status != 200) {
                    Logger.Error("Invalid Icon Resource: %s, Share Name: %s", imgSrc, optionName);
                    result = false;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkShareOptionsURLs(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ShareIcon);
            DriverHelper.ClickWithJS(driver, ShareIcon);
            DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofMillis(500), ShareMenu);

            List<WebElement> elsShareOptions = driver.findElements(ListShareMenuIcons);
            for(WebElement aIcon: elsShareOptions) {
                String href = aIcon.findElement(By.tagName("a")).getAttribute("href");
                String optionName = aIcon.findElement(By.tagName("img")).getAttribute("alt");
                int status = NetworkUtils.URLStatus(href);
                if(href.startsWith("mailto:")) continue;
                if(status != 200 && status != 301) {
                    result = false;
                    Logger.Error("Invalid URL: %s for Social Media: %s", href, optionName);
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkShareOptionsAlt(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ShareIcon);
            DriverHelper.ClickWithJS(driver, ShareIcon);
            DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofMillis(500), ShareMenu);

            String[] allAlts = {"instagram", "twitter", "facebook", "whatsapp", "email"};
            List<WebElement> elsShareOptions = driver.findElements(ListShareMenuIcons);
            for(int i = 0; i < elsShareOptions.size(); i++) {
                String alt = elsShareOptions.get(i).findElement(By.tagName("img")).getAttribute("alt");
                if(!alt.equalsIgnoreCase(allAlts[i])) {
                    Logger.Error("Alt not matches ::: Expected: %s | Actual: %s", allAlts[i], alt);
                    result = false;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkStoryBannerLoaded(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, StoryBanner);
            DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofMillis(500), StoryBanner);
            int status = NetworkUtils.URLStatus(driver.findElement(StoryBanner).getAttribute("src"));
            if(status != 200) result = false;
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

    public boolean checkXXX(TestData testData) {
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

    public boolean checkXX(TestData testData) {
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

    public boolean checkX(TestData testData) {
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
