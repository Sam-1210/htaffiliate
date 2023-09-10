package org.shopnow.tests;

import org.shopnow.annotations.TestDetails;
import org.shopnow.base.BasePage;
import org.shopnow.base.BaseTest;
import org.shopnow.enums.ExecutionType;
import org.shopnow.enums.POM;
import org.shopnow.enums.Platforms;
import org.shopnow.pom.pages.common.CommonHome;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePage extends BaseTest {
    private CommonHome homePage;

    @BeforeMethod
    public void Setup() {
        if(homePage == null) {
            homePage = (CommonHome) BasePage.getInstanceOf(CommonHome.class, POM.PAGES);
        }
    }

    @TestDetails(testcaseID = "ATC1", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerVisibility() {
        Assert.assertTrue(homePage.checkBannerVisibility(),
                "Banner Not Visible");
    }

    @TestDetails(testcaseID = "ATC2", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerCardsCount() {
        Assert.assertTrue(homePage.checkBannerCardsCount(),
                "Number of Banner Cards do not match");
    }

    @TestDetails(testcaseID = "ATC3", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerImages() {
        Assert.assertTrue(homePage.checkBannerImages(),
                "Atleast one of the Banner Images are invalid | check logs above");
    }

    @TestDetails(testcaseID = "ATC4", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerCardsClick() {
        Assert.assertTrue(homePage.checkBannerCardsClick(),
                "Atleast one the banner click/link is invalid | check logs above");
    }

    @TestDetails(testcaseID = "ATC5", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerCardsGA() {
        Assert.assertTrue(homePage.checkBannerCardsGA(),
                "Atleast one the cards GA is invalid | check logs above");
    }

    @TestDetails(testcaseID = "ATC6", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerCardsNoFollow() {
        Assert.assertTrue(homePage.checkBannerCardsNoFollow(),
                "Atleast one the cards rel attribute is invalid | check logs above");
    }

    @TestDetails(testcaseID = "ATC7", executionType = ExecutionType.SANITY,
            platform = {Platforms.WEB, Platforms.MWEB})
    @Test
    public void VerifyBannerPaginationVisible() {
        Assert.assertTrue(homePage.checkBannerPaginationVisible(),
                "Banner Pagination Not Visible");
    }

    @TestDetails(testcaseID = "ATC8", executionType = ExecutionType.SANITY,
            platform = {Platforms.WEB, Platforms.MWEB})
    @Test
    public void VerifyBannerPaginationClickable() {
        Assert.assertTrue(homePage.checkBannerPaginationClickable(),
                "Atleast of the Banner Pagination Not Clickable | check logs above");
    }

    @TestDetails(testcaseID = "ATC9", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyNumberOfStoryCategories() {
        String expected = GetTestData();

        Assert.assertTrue(homePage.checkNumberOfStoryCategories(Integer.parseInt(expected)),
                "Number of Story Categories Shown are Not 7");
    }

    @TestDetails(testcaseID = "ATC10", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyStoryCategoryTitles() {
        Assert.assertTrue(homePage.checkStoryCategoryTitles(),
                "Atleast one of the category title mismatches, check logs above");
    }

    @TestDetails(testcaseID = "ATC11", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyCategoryOrder() {
        Assert.assertTrue(homePage.checkCategoryOrder(),
                "Atleast one of the category order mismatches, check logs above");
    }

    @TestDetails(testcaseID = "ATC12", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyNumberOfHighlightStoryInCategories() {
        Assert.assertTrue(homePage.checkNumberOfHighlightStoryInCategories(),
                "Atleast one category contains more than one highlight story, check logs above");
    }

    @TestDetails(testcaseID = "ATC13", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyNumberOfOtherStoriesInCategories() {
        Assert.assertTrue(homePage.checkNumberOfOtherStoriesInCategories(),
                "Atleast one category contains more than one highlight story, check logs above");
    }

    @TestDetails(testcaseID = "ATC14", executionType = ExecutionType.REGRESSION)
    @Test
    public void VerifyHighlightStories() {
        Assert.assertTrue(homePage.checkHighlightStories(),
                "Atleast one of the highlight story is invalid, check logs above");
    }

    @TestDetails(testcaseID = "ATC15", executionType = ExecutionType.REGRESSION)
    @Test
    public void VerifyOtherStories() {
        Assert.assertTrue(homePage.checkOtherStories(),
                "Atleast one of the Other story is invalid, check logs above");
    }

    @TestDetails(testcaseID = "ATC16", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyCategoryNavigateButtons() {
        Assert.assertTrue(homePage.checkCategoryNavigateButtons(),
                "Atleast one of the Navigate Button is invalid, check logs above");
    }

    @TestDetails(testcaseID = "ATC17", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyContextualWidgets() {
        Assert.assertTrue(homePage.checkContextualWidgets(),
                "Atleast one of the contextual widget is invalid, check logs above");
    }
}
