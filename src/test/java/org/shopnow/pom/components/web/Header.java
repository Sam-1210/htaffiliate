package org.shopnow.pom.components.web;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.shopnow.pom.components.common.CommonHeader;
import org.shopnow.structures.Sitemap;
import org.shopnow.utility.DriverHelper;
import org.shopnow.utility.Logger;

import java.time.Duration;
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
    public static final By _ListSubcategoryItems = By.cssSelector("ul.subcategory-menu > li");


    @Override
    public boolean checkHeaderLogo() {
        return true;
    }

    @Override
    public boolean checkLoginButton() { return true; }

    @Override
    public boolean checkLogoutButton() { return true; }


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
    public boolean checkHeadings() {
        boolean result = true;
        try {
            List<WebElement> categories = driver.findElement(HeaderNavigationContainer).findElements(_ListNavigateItems);
            JSONArray jData = sitemap.getJSONArray("data");
            for(int i = 0; i < categories.size(); i++) {
                JSONObject categoryData = (JSONObject) jData.get(i);
                String actual = categories.get(i).findElement(_Link).getText();
                String expected = categoryData.getString("title");
                boolean match = expected.equals(actual);

                if(!match) Logger.Log("Category Headings Mismatch | Expected: %s, Actual: %s",
                        expected, actual);

                result &= match;
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }

    @Override
    public boolean checkSubHeadings() {
        boolean result = true;
        try {
            List<WebElement> categories = driver.findElement(HeaderNavigationContainer).findElements(_ListNavigateItems);
            JSONArray jData = sitemap.getJSONArray("data");
            for(int i = 0; i < categories.size(); i++) {
                List<WebElement> subcategories = categories.get(i).findElements(_ListSubcategoryItems);
                JSONArray subcategoryData = ((JSONObject) jData.get(i)).getJSONArray("subcategories");
                DriverHelper.MouseOver(driver, categories.get(i));
                for(int j = 0; j < subcategories.size(); j++) {
                    DriverHelper.ExplicitWaitForVisibility(driver, Duration.ofSeconds(1), subcategories.get(j));
                    String actual = subcategories.get(j).findElement(_Link).getText();
                    String expected = ((JSONObject)subcategoryData.get(j)).getString("title");
                    boolean match = expected.equals(actual);

                    if(!match) Logger.Log("Subcategory Headings Mismatch | Category: %s, Expected: %s, Actual: %s",
                            categories.get(i).getText(), expected, actual);

                    result &= match;
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }

    @Override
    public boolean checkNavigation() {
        boolean result = true;
        try {
            JSONArray jData = sitemap.getJSONArray("data");
            String rootURL = applicationProperties.getEnvironment().getURL();
            for(int i = 0; i < jData.length(); i++) {
                String categoryURL = rootURL + "/" + ((JSONObject)jData.get(i)).getString("url");
                DriverHelper.ClickWithJS(driver, By.cssSelector(String.format("li.category-menu:nth-child(%d) > a", i+1)));

                if(!driver.getCurrentUrl().equalsIgnoreCase(categoryURL)) {
                    result = false;
                    Logger.Log("Click Failed For Category %s, Link: %s",
                            ((JSONObject) jData.get(i)).getString("title"), categoryURL);
                }

                JSONArray subcategoryData = ((JSONObject) jData.get(i)).getJSONArray("subcategories");

                for(int j = 0; j < subcategoryData.length(); j++) {

                    String subcategoryURL = categoryURL + "/" + ((JSONObject)subcategoryData.get(j)).getString("url");
                    DriverHelper.ClickWithJS(driver, By.cssSelector(String.format("li.category-menu:nth-child(%d)  ul.subcategory-menu > li:nth-child(%d) > a", i+1, j+1)));

                    if(!driver.getCurrentUrl().equalsIgnoreCase(subcategoryURL)) {
                        result = false;
                        Logger.Log("Click Failed For Subcategory %s of Category: %s, Link: %s",
                                ((JSONObject) subcategoryData.get(j)).getString("title"),
                                ((JSONObject) jData.get(i)).getString("title"), subcategoryURL);
                    }
                }
            }
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }
}
