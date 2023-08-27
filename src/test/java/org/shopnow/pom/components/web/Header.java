package org.shopnow.pom.components.web;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.shopnow.pom.components.common.CommonHeader;
import org.shopnow.structures.Sitemap;
import org.shopnow.utility.DriverHelper;
import org.shopnow.utility.Logger;

import java.util.List;

public class Header extends CommonHeader {
    /**
     * all components exposes locators and keep them static
     * naming conventions for locators
     * List* returns list
     * _* must be used relative to parent
     * el* defines the variable is a WebElement
     */

    // absolute
    public static final By HeaderRoot = By.cssSelector("header.header");
    public static final By HeaderNavigationContainer = By.cssSelector("ul.head-menu-all.container");

    // relative
    public static final By _ListNavigateItems = By.cssSelector("li.category-menu");
    public static final By _ListNavigateItemsLink = By.cssSelector("a");
    public static final By _ListSubcategoryItems = By.cssSelector("ul.subcategory-menu > li");
    public static final By _ListSubcategoryForCategoryLinks = By.cssSelector("a");
    private static final JSONObject sitemap = Sitemap.Get(true);


    @Override
    public boolean checkNumberOfCategories() {
        boolean result = true;
        try {
            int expected = sitemap.getJSONArray("data").length();
            result = driver.findElement(HeaderNavigationContainer).findElements(_ListNavigateItems).size() == expected;
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }

    @Override
    public boolean checkNumberOfSubcategoriesInCategories() {
        boolean result = true;
        try {
            List<WebElement> categories = driver.findElement(HeaderNavigationContainer).findElements(_ListNavigateItems);

            for(int i = 0; i < categories.size(); i++) {
                JSONObject categoryData = (JSONObject) sitemap.getJSONArray("data").get(i);
                int expected = categoryData.getJSONArray("subcategories").length();
                int actual = categories.get(i).findElements(_ListSubcategoryItems).size();
                boolean match = expected == actual;

                if(!match) Logger.Log("Subcategory Number Mismatch | Category: %s, Expected: %d, Actual: %d",
                        categories.get(i).getText(), expected, actual);

                result &= match;
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }

    @Override
    public boolean checkHeadings() { return true; }

    @Override
    public boolean checkNavigation() { return true; }
}
