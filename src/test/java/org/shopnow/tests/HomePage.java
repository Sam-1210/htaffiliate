package org.shopnow.tests;

import org.shopnow.annotations.TestFilter;
import org.shopnow.base.BasePage;
import org.shopnow.base.BaseTest;
import org.shopnow.pom.pages.common.CommonHome;
import org.shopnow.utility.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePage extends BaseTest {
    private CommonHome homePage;

    @BeforeMethod
    public void init() {
        DriverManager.getInstance().getDriver().get("https://shopnow.hindustantimes.com/");
        if(homePage == null) {
            homePage = (CommonHome) BasePage.getInstanceOf(CommonHome.class);
        }
    }

    @TestFilter(platform = {"web", "mweb", "amp"})
    @Test
    public void VerifyNumberOfStoryCategories() {
        Assert.assertTrue(homePage.checkNumberOfStoryCategories(),
                "Number of Story Categories Shown are Not 7");
    }

    @TestFilter(platform = {"web", "mweb", "amp"})
    @Test
    public void VerifyStoryCategoryTitles() {
        Assert.assertTrue(homePage.checkStoryCategoryTitles(),
                "Atleast one of the category title mismatches");
    }

    @TestFilter(platform = {"web", "mweb", "amp"})
    @Test
    public void VerifyNumberOfHighlightStoryInCategories() {
        Assert.assertTrue(homePage.checkNumberOfHighlightStoryInCategories(),
                "Atleast one category contains more than one highlight story");
    }

    @TestFilter(platform = {"web", "mweb", "amp"})
    @Test
    public void VerifyNumberOfOtherStoriesInCategories() {
        Assert.assertTrue(homePage.checkNumberOfOtherStoriesInCategories(),
                "Atleast one category contains more than one highlight story");
    }
}
