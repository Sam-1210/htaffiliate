package org.shopnow.pom.components.common;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.shopnow.base.BasePage;
import org.shopnow.structures.Sitemap;
import org.shopnow.utility.Logger;

import java.util.List;
import java.util.Map;

public class CommonHeader extends BasePage {
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

    /**
     * mandatory test | Platform Specifics
     * make abstract
     */

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

    public boolean checkNavigation() { return true; }

    /**
     * default, common tests for all
     */
    public boolean checkHeaderLogo() {
        boolean result = true;
        for(Map<String, String> entry : flattenedSitemap) {
            driver.get(entry.get("url"));
            boolean match = true;
            match &= driver.findElement(HeaderLogo).isDisplayed();
            if(!match) Logger.Log("Header logo not displayed on page: %s", entry.get("url"));
            result &= match;
        }

        return true;
    }
}
