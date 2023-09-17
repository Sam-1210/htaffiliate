package org.shopnow.pom.components.common;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.shopnow.base.BasePage;
import org.shopnow.structures.Sitemap;
import org.shopnow.utility.DriverHelper;
import org.shopnow.utility.Logger;

import java.util.List;
import java.util.Map;

public abstract class CommonHeader extends BasePage {
    /**
     * naming conventions for locators
     * List* returns list
     * _* must be used relative to parent
     * el* defines the variable is a WebElement
     */
    protected static final JSONObject sitemap = Sitemap.Get(true);
    protected final List<Map<String, String>> flattenedSitemap = Sitemap.GetFlattenedSitemap(sitemap, applicationProperties.getEnvironment());

    /**
     * locators | common for all platforms
     */
    // absolute
    protected final By HeaderLogo = By.cssSelector("div.logo > a > img");

    // relative
    public static final By _Link = By.tagName("a");

    /**
     * mandatory test | Platform Specifics
     * make abstract
     */
    public abstract boolean checkNavigation();

    /**
     * optional tests | Platform Specific
     * provide body returning true
     */
    public boolean checkLoginButton() { return true; }
    public boolean checkLogoutButton() { return true; }

    public boolean checkNumberOfCategories() { return true; }
    public boolean checkNumberOfSubcategoriesInCategories() { return true; }
    public boolean checkHeadings() { return true; }
    public boolean checkSubHeadings() { return true; }



    /**
     * default, common tests for all
     */
    public boolean checkHeaderLogo() {
        String homeURL = applicationProperties.getEnvironment().getURL();
        boolean result = true;
        for(Map<String, String> entry : flattenedSitemap) {
            driver.get(entry.get("url"));
            boolean match = true;
            match &= driver.findElement(HeaderLogo).isDisplayed();
            if(!match) Logger.Log("Header logo not displayed on page: %s", entry.get("url"));
            result &= match;

            DriverHelper.ClickWithJS(driver, HeaderLogo);
            match = driver.getCurrentUrl().equalsIgnoreCase(homeURL);
            if(!match) Logger.Log("Header Logo Click Redirection is Invalid on page: %s, url:%s", entry.get("title"), entry.get("url"));
            result &= match;
        }

        return result;
    }
}
