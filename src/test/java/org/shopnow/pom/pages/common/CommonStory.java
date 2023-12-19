package org.shopnow.pom.pages.common;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.java.Log;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.shopnow.base.BasePage;
import org.shopnow.structures.TestData;
import org.shopnow.utility.DriverHelper;
import org.shopnow.utility.DriverManager;
import org.shopnow.utility.Logger;
import org.shopnow.utility.NetworkUtils;


public class CommonStory extends BasePage {
    private final String regexIndianPrice = "^₹ (\\d{1,3}(,\\d{3})*(\\.\\d{2})?)$";
    private final String regexProductCarouselMRP = "^₹(\\d{1,3}(,\\d{3})*(\\.\\d{2})?)$";
    private final String regexProductCarouselDiscount = "^(100|[1-9][0-9]?)% off$";
    private final String regexProductCarouselButtonPrice = "^₹ (\\d{1,3}(,\\d{3})*(\\.\\d{2})?) from$";
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
    private static final By ListSummaryProductPriceTableRows = By.cssSelector("div.summary > div.productTableContainer > table > tbody > tr");
    private static final By ProductCarouselParent = By.cssSelector("div.summary + div > div");
    private static final By ProductCarouselTitle = By.cssSelector("div.summary + div > div > h2");
    private static final By ProductCarouselCardContainerStatic = By.cssSelector("div.summary + div > div > div.product_slider");
    private static final By ProductCarouselCardContainerDynamic = By.cssSelector("div.summary + div > div > div.product_slider > div.swiper-wrapper");
    private static final By ListProductCarouselCards = By.cssSelector("div.summary + div > div > div.product_slider > div.swiper-wrapper div.swiper-slide");
    private static final Function<Integer, By> ProductCarouselCard = (Integer index) -> By.cssSelector(String.format("div.summary + div > div > div.product_slider > div.swiper-wrapper div.swiper-slide:nth-child(%d)", index));
    private static final By ProductCarouselButtonPrev = By.cssSelector("div.summary + div > div > div.product_slider > div.swiper-button-prev");
    private static final By ProductCarouselButtonNext = By.cssSelector("div.summary + div > div > div.product_slider > div.swiper-button-next");
    private static final By ListListicleCards = By.cssSelector("div.main_product");

    // relative
    private static final By _SummaryProductPriceTableLinkData = By.cssSelector("td:first-child a");
    private static final By _SummaryProductPriceTablePriceData = By.cssSelector("td:last-child");
    private static final By _ProductCarouselCardOurPickBadge = By.cssSelector("span.our_pick");
    private static final By _ProductCarouselCardImage = By.cssSelector("span.product_box_img img");
    private static final By _ProductCarouselCardBuyButton = By.tagName("button");
    private static final By _ProductCarouselCardTitle = By.cssSelector("p.h3-heading");
    private static final By _ProductCarouselCardMRP = By.cssSelector("span.pricing_cut");
    private static final By _ProductCarouselCardDiscount = By.cssSelector("span.pricing_off");
    private static final By __ProductCarouselCardVendorLogo = By.tagName("img");
    private static final By _ListiclesOurPickBadge = By.cssSelector("span.our_pick");
    private static final By _ListiclesCardImage = By.cssSelector("span.main_product_img img");
    private static final By _ListListiclesCardBuyButton = By.tagName("button");
    private static final By _ListiclesCardTitle = By.cssSelector("div.main_product_content > h2");
    private static final By _ListiclesCardMRP = By.cssSelector("span.pricing_cut");
    private static final By _ListiclesCardDiscount = By.cssSelector("span.pricing_off");
    private static final By __ListiclesCardVendorLogo = By.tagName("img");

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
                if(status != 200) {
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

            Logger.Log(elsShareOptions.size());
            for(WebElement elIcon:elsShareOptions) {
                WebElement elImg = elIcon.findElement(By.tagName("img"));
                DriverHelper.ScrollWithJS(driver, elIcon);
                Thread.sleep(50);
                String imgSrc = elImg.getAttribute("src");
                int status = NetworkUtils.URLStatus(imgSrc);
                if(status != 200) {
                    Logger.Error("Invalid Icon Resource: %s", imgSrc);
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
                if(status != 200) {
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

    public boolean checkSummaryTitleAndParagraph(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            if(!driver.findElement(SummaryPara).isDisplayed()) {
                Logger.Error("Summary Title is not visible");
                result = false;
            }

            if(!driver.findElement(SummaryPara).isDisplayed()) {
                Logger.Error("Summary paragraph not visible");
                result = false;
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkSummaryExpand(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, SummaryReadMore);
            DriverHelper.ClickWithJS(driver, SummaryReadMore);
            DriverHelper.ForceWait(Duration.ofMillis(100));

            if(!driver.findElement(SummaryPara).isDisplayed()) {
                Logger.Error("Summary paragraph not visible after clicking read more");
                result = false;
            }

            if(!driver.findElement(SummaryProductPriceTable).isDisplayed()) {
                Logger.Error("Product Price Table is not visible after clicking on read more");
                result = false;
            }

            if(!driver.findElement(SummaryReadLess).isDisplayed()) {
                Logger.Error("Read less button is not displayed after expanding summary");
                result = false;
            }

            if(driver.findElement(SummaryReadMore).isDisplayed()) {
                Logger.Error("Read more button is still visible after expanding summary");
                result = false;
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkSummaryCollapse(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, SummaryReadMore);
            DriverHelper.ClickWithJS(driver, SummaryReadMore);
            DriverHelper.ForceWait(Duration.ofMillis(100));
            if(!driver.findElement(SummaryReadLess).isDisplayed()) {
                Logger.Error("Read less button is not displayed after expanding summary, test Halted");
                return false;
            }

            DriverHelper.ClickWithJS(driver, SummaryReadLess);
            DriverHelper.ForceWait(Duration.ofMillis(100));

            if(!driver.findElement(SummaryPara).isDisplayed()) {
                Logger.Error("Summary paragraph not visible after clicking read less");
                result = false;
            }

            if(driver.findElement(SummaryProductPriceTable).isDisplayed()) {
                Logger.Error("Product Price Table is visible after clicking on read less");
                result = false;
            }

            if(driver.findElement(SummaryReadLess).isDisplayed()) {
                Logger.Error("Read less button is not displayed after collpasing summary");
                result = false;
            }

            if(!driver.findElement(SummaryReadMore).isDisplayed()) {
                Logger.Error("Read more button is not visible after collapsing summary");
                result = false;
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProductPriceTableRowCount(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();
        int expected = jTestData.getInt("productCount");

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ClickWithJS(driver, SummaryReadMore);
            DriverHelper.ForceWait(Duration.ofMillis(100));
            int actual = driver.findElements(ListSummaryProductPriceTableRows).size();
            if(actual != expected) {
                Logger.Error("Product Count Mismatch ::: Expected: %d | Actual: %d", expected, actual);
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProductPriceTableProductLinks(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ClickWithJS(driver, SummaryReadMore);
            DriverHelper.ForceWait(Duration.ofMillis(100));
            List<WebElement> rows = driver.findElements(ListSummaryProductPriceTableRows);

            for(int i = 0; i < rows.size(); i++) {
                WebElement ProdLink = rows.get(i).findElement(_SummaryProductPriceTableLinkData);
                String href = ProdLink.getAttribute("data-vars-url");
                int status = NetworkUtils.URLStatus(href);
                if(status != 200 && status != 301) {
                    Logger.Log("Invalid URL in Product Price Table: %s", href);
                    result = false;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProductPriceTablePriceFormat(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ClickWithJS(driver, SummaryReadMore);
            DriverHelper.ForceWait(Duration.ofMillis(100));
            List<WebElement> rows = driver.findElements(ListSummaryProductPriceTableRows);
            Pattern pattern = Pattern.compile(regexIndianPrice);

            for(int i = 0; i < rows.size(); i++) {
                WebElement ProdPrice = rows.get(i).findElement(_SummaryProductPriceTablePriceData);
                String price = ProdPrice.getText();
                Matcher matcher = pattern.matcher(price);
                if(!price.trim().equalsIgnoreCase("Get Price")
                && !matcher.matches()) {
                    Logger.Error("Wrong Formatted Price: %s", price);
                    result = false;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProductCarouselVisibleAndTitle(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ProductCarouselParent);
            if(!driver.findElement(ProductCarouselParent).isDisplayed()) {
                Logger.Error("Product carousel is not visible");
                result = false;
            }

            String actual = driver.findElement(ProductCarouselTitle).getText();
            String expected = "Products included in this article";
            if(!actual.equalsIgnoreCase(expected)) {
                Logger.Error("Title do not match ::: Expected: `%s` | Actual: `%s`", expected, actual);
                result = false;
            }

        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProductCarouselProdCount(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();
        int expected = jTestData.getInt("productCount");

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            int actual = driver.findElements(ListProductCarouselCards).size();
            if(actual != expected) {
                Logger.Error("Product Count Mismatch ::: Expected: %d | Actual: %d", expected, actual);
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProdCarouselSwipe(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();
        int sliderWait = 800;

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ProductCarouselCardContainerStatic);

            if(driver.findElement(ProductCarouselButtonPrev).isDisplayed()) {
                Logger.Error("Left Arrow is being displayed in default case");
                result = false;
            }

            List<WebElement> prodCards = driver.findElements(ListProductCarouselCards);
            WebElement prodCardContainer = driver.findElement(ProductCarouselCardContainerStatic);
            int countOfVisible = 0;
            for (WebElement prodCard : prodCards) {
                if (DriverHelper.IsElementInBounds(driver, prodCard, prodCardContainer)) countOfVisible++;
            }

            if(prodCards.size() == countOfVisible) {
                if(driver.findElement(ProductCarouselButtonPrev).isDisplayed()) {
                    Logger.Log("Left arrow is being displayed even when all prods are already in viewport");
                    result = false;
                }

                if(driver.findElement(ProductCarouselButtonNext).isDisplayed()) {
                    Logger.Log("Right arrow is being displayed even when all prods are already in viewport");
                    result = false;
                }
            }

            for(int i = countOfVisible; i < prodCards.size(); i++) {
                Point old = prodCards.get(i).getLocation();

                DriverHelper.ClickWithJS(driver, ProductCarouselButtonNext);

                if(i == countOfVisible) {
                    DriverHelper.ForceWait(Duration.ofMillis(100));
                    if(!driver.findElement(ProductCarouselButtonPrev).isDisplayed()) {
                        Logger.Error("Swiper back button not visible after right swipe");
                        result = false;
                    }
                }

                DriverHelper.ForceWait(Duration.ofMillis(sliderWait));

                if(!DriverHelper.IsElementInBounds(driver, prodCards.get(i), prodCardContainer)) {
                    Logger.Log("Next card doesn't become visible on right swipe");
                    result = false;
                }

                if(!(prodCards.get(i).getLocation().x < old.x)) {
                    Logger.Log("Next card's position has not changed");
                    result = false;
                }


                if(i == prodCards.size() - 1) {
                    DriverHelper.ForceWait(Duration.ofMillis(100));
                    if(driver.findElement(ProductCarouselButtonNext).isDisplayed()) {
                        Logger.Error("Swiper right button is visible even at end of swiper");
                        result = false;
                    }
                    if(!driver.findElement(ProductCarouselButtonPrev).isDisplayed()) {
                        Logger.Error("Swiper left button is not visible at end of swiper");
                        result = false;
                    }
                }
            }

            // do opposite swipe on prev button
            for(int i = prodCards.size() - 1; i >= countOfVisible; i--) {
                Point old = prodCards.get(i).getLocation();
                DriverHelper.ClickWithJS(driver, ProductCarouselButtonPrev);

                DriverHelper.ForceWait(Duration.ofMillis(sliderWait));
                int index = i - countOfVisible;
                if(!DriverHelper.IsElementInBounds(driver, prodCards.get(index), prodCardContainer)) {
                    Logger.Log("previous card doesn't become visible on left swipe");
                    result = false;
                }

                if(!(prodCards.get(i).getLocation().x > old.x)) {
                    Logger.Log("previous card's position has not changed");
                    result = false;
                }


                if(i == countOfVisible) {
                    DriverHelper.ForceWait(Duration.ofMillis(100));
                    if(!driver.findElement(ProductCarouselButtonNext).isDisplayed()) {
                        Logger.Error("Swiper right button is not  visible even at beginning of swiper");
                        result = false;
                    }
                    if(driver.findElement(ProductCarouselButtonPrev).isDisplayed()) {
                        Logger.Error("Swiper left button is visible at beginning of swiper");
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProdCarouselCardOurPickBadge(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ProductCarouselCardContainerStatic);
            List<WebElement> prodCards = driver.findElements(ListProductCarouselCards);
            if(prodCards.size() == 0) return true;
            List<WebElement> ourPickBadge = prodCards.get(0).findElements(_ProductCarouselCardOurPickBadge);
            if(ourPickBadge.size() == 0) {
                Logger.Error("Our pick badge is not shown for first product in carousel");
                result = false;
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProdCarouselCardGetPriceCase(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ProductCarouselCardContainerStatic);
            List<WebElement> prodCards = driver.findElements(ListProductCarouselCards);

            for(WebElement card: prodCards) {
                WebElement buyButton = card.findElement(_ProductCarouselCardBuyButton);
                String buttonText = buyButton.getText();
                String prodTitle = card.findElement(_ProductCarouselCardTitle).getText();
                Pattern pattern = Pattern.compile(regexProductCarouselButtonPrice);
                Matcher matcher = pattern.matcher(buttonText);
                if(!matcher.matches())
                {
                    if(buttonText.trim().equalsIgnoreCase("Get Price from")) {
                        List<WebElement> mrp = card.findElements(_ProductCarouselCardMRP);
                        List<WebElement> discount = card.findElements(_ProductCarouselCardDiscount);
                        if(mrp.size() > 0 && mrp.get(0).isDisplayed()) {
                            Logger.Error("MRP is displayed while button shows get price, product: `%s`", prodTitle);
                            result = false;
                        }
                        if(discount.size() > 0 && discount.get(0).isDisplayed()) {
                            Logger.Error("Discount is displayed while button shows get price, product: `%s`", prodTitle);
                            result = false;
                        }
                    } else {
                        Logger.Error("Wrong Formatted Price: `%s`, product: `%s`", buttonText, prodTitle);
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProdCarouselCardNumericalPriceCase(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ProductCarouselCardContainerStatic);
            List<WebElement> prodCards = driver.findElements(ListProductCarouselCards);

            for(WebElement card: prodCards) {
                WebElement buyButton = card.findElement(_ProductCarouselCardBuyButton);
                String buttonText = buyButton.getText();
                String prodTitle = card.findElement(_ProductCarouselCardTitle).getText();
                Pattern pattern = Pattern.compile(regexProductCarouselButtonPrice);
                Matcher matcher = pattern.matcher(buttonText);
                if(matcher.matches())
                {
                    List<WebElement> mrp = card.findElements(_ProductCarouselCardMRP);
                    List<WebElement> discount = card.findElements(_ProductCarouselCardDiscount);
                    if((mrp.size() == 0 || !mrp.get(0).isDisplayed())
                    && (discount.size() > 0 && discount.get(0).isDisplayed())) {
                        Logger.Error("MRP is not displayed while discount is displayed, product: `%s`", prodTitle);
                        result = false;
                    }
                    if((mrp.size() > 0 && mrp.get(0).isDisplayed())
                            && (discount.size() == 0 || !discount.get(0).isDisplayed())) {
                        Logger.Error("Discount is displayed while MRP is not displayed, product: `%s`", prodTitle);
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProdCarouselCardPriceFormat(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ProductCarouselCardContainerStatic);
            List<WebElement> prodCards = driver.findElements(ListProductCarouselCards);

            for(WebElement card: prodCards) {
                WebElement buyButton = card.findElement(_ProductCarouselCardBuyButton);
                String buttonText = buyButton.getText();
                String prodTitle = card.findElement(_ProductCarouselCardTitle).getText();
                Pattern patternButtonPrice = Pattern.compile(regexProductCarouselButtonPrice);
                Matcher matcherButtonPrice = patternButtonPrice.matcher(buttonText);

                List<WebElement> mrp = card.findElements(_ProductCarouselCardMRP);
                if(matcherButtonPrice.matches()) {
                    if(mrp.size() > 0 && mrp.get(0).isDisplayed()) {
                        String mrpText = mrp.get(0).getText();
                        Pattern patternMRP = Pattern.compile(regexProductCarouselMRP);
                        Matcher matcherMRP = patternMRP.matcher(mrpText);
                        if(!matcherMRP.matches()) {
                            Logger.Error("MRP is displayed in wrong format, product: `%s`", prodTitle);
                            result = false;
                        }
                    }
                } else if(!buttonText.trim().equalsIgnoreCase("Get Price from")) {
                    Logger.Error("Wrong Formatted Price: `%s`, product: `%s`", buttonText, prodTitle);
                    result = false;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProdCarouselDiscountFormat(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ProductCarouselCardContainerStatic);
            List<WebElement> prodCards = driver.findElements(ListProductCarouselCards);

            for(WebElement card: prodCards) {
                WebElement buyButton = card.findElement(_ProductCarouselCardBuyButton);
                String buttonText = buyButton.getText();
                String prodTitle = card.findElement(_ProductCarouselCardTitle).getText();
                Pattern patternButtonPrice = Pattern.compile(regexProductCarouselButtonPrice);
                Matcher matcherButtonPrice = patternButtonPrice.matcher(buttonText);

                List<WebElement> discount = card.findElements(_ProductCarouselCardDiscount);
                if(matcherButtonPrice.matches()) {
                    if (discount.size() > 0 && discount.get(0).isDisplayed()) {
                        String discountText = discount.get(0).getText();
                        Pattern patternDis = Pattern.compile(regexProductCarouselDiscount);
                        Matcher matcherDis = patternDis.matcher(discountText);
                        if (!matcherDis.matches()) {
                            Logger.Error("Discount is displayed in wrong format, product: `%s`", prodTitle);
                            result = false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProdCarouselCardTitleVisiblity(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ProductCarouselCardContainerStatic);
            List<WebElement> prodCards = driver.findElements(ListProductCarouselCards);

            for(WebElement card: prodCards) {
                WebElement prodTitle = card.findElement(_ProductCarouselCardTitle);

                if(!prodTitle.isDisplayed()) {
                    Logger.Error("Product Title is not displayed");
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProdCarouselCardImagesLoaded(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ProductCarouselCardContainerStatic);
            List<WebElement> prodCards = driver.findElements(ListProductCarouselCards);

            for(WebElement card: prodCards) {
                String prodTitle = card.findElement(_ProductCarouselCardTitle).getText();
                String href = card.findElement(_ProductCarouselCardImage).getAttribute("src");
                int status = NetworkUtils.URLStatus(href);
                if(status != 200) {
                    Logger.Error("Image Not Loaded, product: `%s`", prodTitle);
                    result = false;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkProdCarouselCardVendorLogoLoaded(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();
        Set<String> logos = new HashSet<>();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ProductCarouselCardContainerStatic);
            List<WebElement> prodCards = driver.findElements(ListProductCarouselCards);

            for(WebElement card: prodCards) {
                WebElement cardBuyButton = card.findElement(_ProductCarouselCardBuyButton);
                WebElement cardVendorLogo = cardBuyButton.findElement(__ProductCarouselCardVendorLogo);

                logos.add(cardVendorLogo.getAttribute("src"));
            }

            for(String logoSrc : logos) {
                int status = NetworkUtils.URLStatus(logoSrc);
                if(status != 200) {
                    Logger.Error("Vendor Logo Not Loaded, logo: `%s`", logoSrc);
                    result = false;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkListiclesCount(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();
        int expected = jTestData.getInt("productCount");

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            int actual = driver.findElements(ListListicleCards).size();
            if(actual != expected) {
                Logger.Error("Listicles Count Mismatch ::: Expected: %d | Actual: %d", expected, actual);
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }


    public boolean checkListiclesOurPickBadge(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            List<WebElement> listicles = driver.findElements(ListListicleCards);
            if(listicles.size() == 0) return true;
            List<WebElement> ourPickBadge = listicles.get(0).findElements(_ListiclesOurPickBadge);
            if(ourPickBadge.size() == 0) {
                Logger.Error("Our pick badge is not shown for first listicle");
                result = false;
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkListiclesGetPriceCase(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            //DriverHelper.ScrollWithJS(driver, ProductCarouselCardContainerStatic);
            List<WebElement> prodCards = driver.findElements(ListProductCarouselCards);

            for(WebElement card: prodCards) {
                WebElement buyButton = card.findElement(_ProductCarouselCardBuyButton);
                String buttonText = buyButton.getText();
                String prodTitle = card.findElement(_ProductCarouselCardTitle).getText();
                Pattern pattern = Pattern.compile(regexProductCarouselButtonPrice);
                Matcher matcher = pattern.matcher(buttonText);
                if(!matcher.matches())
                {
                    if(buttonText.trim().equalsIgnoreCase("Get Price from")) {
                        List<WebElement> mrp = card.findElements(_ProductCarouselCardMRP);
                        List<WebElement> discount = card.findElements(_ProductCarouselCardDiscount);
                        if(mrp.size() > 0 && mrp.get(0).isDisplayed()) {
                            Logger.Error("MRP is displayed while button shows get price, product: `%s`", prodTitle);
                            result = false;
                        }
                        if(discount.size() > 0 && discount.get(0).isDisplayed()) {
                            Logger.Error("Discount is displayed while button shows get price, product: `%s`", prodTitle);
                            result = false;
                        }
                    } else {
                        Logger.Error("Wrong Formatted Price: `%s`, product: `%s`", buttonText, prodTitle);
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkListiclesNumericalPriceCase(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            List<WebElement> prodCards = driver.findElements(ListProductCarouselCards);

            for(WebElement card: prodCards) {
                WebElement buyButton = card.findElement(_ProductCarouselCardBuyButton);
                String buttonText = buyButton.getText();
                String prodTitle = card.findElement(_ProductCarouselCardTitle).getText();
                Pattern pattern = Pattern.compile(regexProductCarouselButtonPrice);
                Matcher matcher = pattern.matcher(buttonText);
                if(matcher.matches())
                {
                    List<WebElement> mrp = card.findElements(_ProductCarouselCardMRP);
                    List<WebElement> discount = card.findElements(_ProductCarouselCardDiscount);
                    if((mrp.size() == 0 || !mrp.get(0).isDisplayed())
                            && (discount.size() > 0 && discount.get(0).isDisplayed())) {
                        Logger.Error("MRP is not displayed while discount is displayed, product: `%s`", prodTitle);
                        result = false;
                    }
                    if((mrp.size() > 0 && mrp.get(0).isDisplayed())
                            && (discount.size() == 0 || !discount.get(0).isDisplayed())) {
                        Logger.Error("Discount is displayed while MRP is not displayed, product: `%s`", prodTitle);
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkListiclesPriceFormat(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            List<WebElement> listicles = driver.findElements(ListListicleCards);

            for(WebElement card: listicles) {
                List<WebElement> buyButtons = card.findElements(_ListListiclesCardBuyButton);
                for(WebElement buyButton:buyButtons) {
                    String buttonText = buyButton.getText();
                    String prodTitle = card.findElement(_ListiclesCardTitle).getText();
                    Pattern patternButtonPrice = Pattern.compile(regexProductCarouselButtonPrice);
                    Matcher matcherButtonPrice = patternButtonPrice.matcher(buttonText);

                    List<WebElement> mrp = card.findElements(_ListiclesCardMRP);
                    if(matcherButtonPrice.matches()) {
                        if(mrp.size() > 0 && mrp.get(0).isDisplayed()) {
                            String mrpText = mrp.get(0).getText();
                            Pattern patternMRP = Pattern.compile(regexProductCarouselMRP);
                            Matcher matcherMRP = patternMRP.matcher(mrpText);
                            if(!matcherMRP.matches()) {
                                Logger.Error("MRP is displayed in wrong format, product: `%s`, found: `%s`", prodTitle, mrpText);
                                result = false;
                            }
                        }
                    } else if(!buttonText.trim().equalsIgnoreCase("Get Price from")) {
                        Logger.Error("Wrong Formatted Price: `%s`, product: `%s`", buttonText, prodTitle);
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkListiclesDiscountFormat(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            List<WebElement> listicles = driver.findElements(ListListicleCards);

            for(WebElement card: listicles) {
                List<WebElement> buyButtons = card.findElements(_ListListiclesCardBuyButton);
                boolean isNumerical = false;
                String prodTitle = card.findElement(_ListiclesCardTitle).getText();

                for(WebElement buyButton: buyButtons)
                {
                    String buttonText = buyButton.getText();
                    Pattern patternButtonPrice = Pattern.compile(regexProductCarouselButtonPrice);
                    Matcher matcherButtonPrice = patternButtonPrice.matcher(buttonText);
                    if(matcherButtonPrice.matches()) isNumerical = true;
                }
                List<WebElement> discount = card.findElements(_ListiclesCardDiscount);
                if(isNumerical) {
                    if (discount.size() > 0 && discount.get(0).isDisplayed()) {
                        String discountText = discount.get(0).getText();
                        Pattern patternDis = Pattern.compile(regexProductCarouselDiscount);
                        Matcher matcherDis = patternDis.matcher(discountText);
                        if (!matcherDis.matches()) {
                            Logger.Error("Discount is displayed in wrong format, product: `%s`", prodTitle);
                            result = false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkListiclesTitleVisiblity(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            DriverHelper.ScrollWithJS(driver, ProductCarouselCardContainerStatic);
            List<WebElement> listicles = driver.findElements(ListListicleCards);

            for(WebElement card: listicles) {
                WebElement prodTitle = card.findElement(_ListiclesCardTitle);

                if(!prodTitle.isDisplayed()) {
                    Logger.Error("Product Title is not displayed");
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkListiclesProdImagesLoaded(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            List<WebElement> listicles = driver.findElements(ListListicleCards);

            for(WebElement card: listicles) {
                DriverHelper.ScrollWithJS(driver, card);
                DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofSeconds(2), card);
                DriverHelper.ForceWait(Duration.ofMillis(500));
                String prodTitle = card.findElement(_ListiclesCardTitle).getText();
                String href = card.findElement(_ListiclesCardImage).getAttribute("src");
                int status = NetworkUtils.URLStatus(href);
                if(status != 200) {
                    Logger.Error("Image Not Loaded, product: `%s`", prodTitle);
                    result = false;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }
        return result;
    }

    public boolean checkListiclesVendorLogoLoaded(TestData testData) {
        boolean result = true;
        JSONObject jTestData = (JSONObject)testData.GetData();
        Set<String> logos = new HashSet<>();

        try {
            DriverHelper.NavigateTo(driver, jTestData.getString("uri"));
            List<WebElement> listicles = driver.findElements(ListListicleCards);

            for(WebElement card: listicles) {
                DriverHelper.ScrollWithJS(driver, card);
                DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofSeconds(1), card);
                DriverHelper.ForceWait(Duration.ofMillis(500));
                List<WebElement> cardBuyButtons = card.findElements(_ListListiclesCardBuyButton);
                for(WebElement cardBuyButton: cardBuyButtons)
                {
                    WebElement cardVendorLogo = cardBuyButton.findElement(__ListiclesCardVendorLogo);
                    logos.add(cardVendorLogo.getAttribute("src"));
                }
            }

            for(String logoSrc : logos) {
                int status = NetworkUtils.URLStatus(logoSrc);
                if(status != 200) {
                    Logger.Error("Vendor Logo Not Loaded, logo: `%s`", logoSrc);
                    result = false;
                }
            }
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
}
