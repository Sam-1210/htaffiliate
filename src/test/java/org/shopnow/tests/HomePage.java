package org.shopnow.tests;

import org.shopnow.annotations.TestFilter;
import org.shopnow.base.BasePage;
import org.shopnow.base.BaseTest;
import org.shopnow.enums.POM;
import org.shopnow.pom.pages.common.CommonHome;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.utility.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePage extends BaseTest {
    private CommonHome homePage;

    @BeforeMethod
    public void init() {
        if(homePage == null) {
            homePage = (CommonHome) BasePage.getInstanceOf(CommonHome.class, POM.PAGES);
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
                "Atleast one of the category title mismatches, check logs above");
    }

    @TestFilter(platform = {"web", "mweb", "amp"})
    @Test
    public void VerifyNumberOfHighlightStoryInCategories() {
        Assert.assertTrue(homePage.checkNumberOfHighlightStoryInCategories(),
                "Atleast one category contains more than one highlight story, check logs above");
    }

    @TestFilter(platform = {"web", "mweb", "amp"})
    @Test
    public void VerifyNumberOfOtherStoriesInCategories() {
        Assert.assertTrue(homePage.checkNumberOfOtherStoriesInCategories(),
                "Atleast one category contains more than one highlight story, check logs above");
    }

    @TestFilter(platform = {"web", "mweb", "amp"})
    @Test
    public void VerifyHighlightStories() {
        Assert.assertTrue(homePage.checkHighlightStories(),
                "Atleast one of the highlight story is invalid, check logs above");
    }

    @TestFilter(platform = {"web", "mweb", "amp"})
    @Test
    public void VerifyOtherStories() {
        Assert.assertTrue(homePage.checkOtherStories(),
                "Atleast one of the Other story is invalid, check logs above");
    }

    @TestFilter(platform = {"web", "mweb", "amp"})
    @Test
    public void VerifyCategoryNavigateButtons() {
        Assert.assertTrue(homePage.checkCategoryNavigateButtons(),
                "Atleast one of the Navigate Button is invalid, check logs above");
    }
}
