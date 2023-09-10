package org.shopnow.pom.components.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.shopnow.base.BasePage;
import org.shopnow.utility.DriverHelper;
import org.shopnow.utility.Logger;
import org.shopnow.utility.TabHelper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
class WdgtCard {
    @Getter private String Title;
    @Getter private String Discount;
    @Getter private String Rating;
    @Getter private String RatingCount;
    @Getter private String AfterDiscount;
    @Getter private String BeforeDiscount;

    public static WdgtCard Get(String amazonURL) {
        WdgtCard aCard = null;
        // use amazon api to fetch these details
        return aCard;
    }
}

public class CommonContextualWidget extends BasePage {
    List<WdgtCard> cardsData;
    int Index;
    String Category;
    public CommonContextualWidget(int Index, String Category) {
        this.Index = Index;
        this.Category = Category;
        cardsData = new ArrayList<>();
        try {
            List<WebElement> elsOfferSliders = driver.findElements(P_ListCategoryContextualWidgets);
            List<WebElement> elsCards = elsOfferSliders.get(Index).findElements(Card);
            List<String> urls = new ArrayList<>();
            for (WebElement aCard : elsCards) {
                urls.add(aCard.findElement(_Link).getAttribute("href"));
            }
            for (String url : urls) {
                cardsData.add(WdgtCard.Get(url));
            }
        } catch (Exception e) {
            Logger.Except(e);
        }
    }

    // locators
    public By P_ListCategoryContextualWidgets = By.cssSelector("section.ofersliderbox > div.offerSlideRow");
    public final By DataVarsArticle = By.cssSelector("div.offerSlideCols > article");
    public final By Card = By.cssSelector("div.offerSlideCols > article > div.offercard");
    public final By _Link = By.tagName("a");
    public final By _DiscountBadge = By.cssSelector("div.offerbadge > div.offerValue");
    public final By _Image = By.cssSelector("div.offerpic > img");
    public final By _Title = By.cssSelector("div.amazonContent");
    public final By _beforeDiscountPrice = By.cssSelector("div.offerprice > span.beforediscount > span:nth-child(2)");
    public final By _AfterDiscountPrice = By.cssSelector("div.offerprice > span.afterdiscount > span:nth-child(2)");
    public final By _userRating = By.cssSelector("p.product-ratings-box > span.product-ratings");
    public final By _RatingCount = By.cssSelector("p.product-ratings-box");
    public final By _VendorLogo = By.cssSelector("div.card-logo-button > img");
    public final By _BuyButton = By.cssSelector("div.card-logo-button > button");

    public boolean checkNumberOfCards() {
        Logger.Log("Verifying Number of Cards For Widget Number: %d, For Category: %s", Index, Category);
        boolean result = true;
        try {
            List<WebElement> elsOfferSliders = driver.findElements(P_ListCategoryContextualWidgets);
            int expected = 4;
            int actual = elsOfferSliders.get(Index).findElements(DataVarsArticle).size();
            result = actual == expected;
            if (!result) Logger.Log("Number of offercards in category %s mismatch | Expected: %s, Actual: %s",
                    Category, expected, actual);
        } catch (Exception e) {
            Logger.Log(e);
            result = false;
        }

        return result;
    }

    public boolean checkClick() {
        Logger.Log("Verifying Click For Widget Number: %d, For Category: %s", Index, Category);
        boolean result = true;

        try {
            List<WebElement> elsOfferSliders = driver.findElements(P_ListCategoryContextualWidgets);
            List<WebElement> elsCards = elsOfferSliders.get(Index).findElements(Card);

            for(WebElement card : elsCards) {
                boolean match = true;
                String URL = card.findElement(_Link).getAttribute("href");

                TabHelper.getInstance(driver).OpenLinkInNewTab(URL);
                {
                    try{
                        DriverHelper.ExplicitWaitForPageLoad(driver, Duration.ofSeconds(2));
                        match = driver.getCurrentUrl().startsWith("https://www.amazon.in/dp/");
                    } catch (Exception e) {
                        Logger.Log("Click not redirecting to correct Page | URL: %s", URL);
                        match = false;
                    }
                }
                TabHelper.getInstance(driver).close();

                result &= match;
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }
}
