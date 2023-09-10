package org.shopnow.tests;

import org.shopnow.annotations.TestFilter;
import org.shopnow.base.BasePage;
import org.shopnow.base.BaseTest;
import org.shopnow.enums.ExecutionType;
import org.shopnow.enums.POM;
import org.shopnow.enums.Platforms;
import org.shopnow.pom.pages.common.CommonHome;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.utility.DriverManager;
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

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerVisibility() {
        Assert.assertTrue(homePage.checkBannerVisibility(),
                "Banner Not Visible");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerCardsCount() {
        Assert.assertTrue(homePage.checkBannerCardsCount(),
                "Number of Banner Cards do not match");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerImages() {
        Assert.assertTrue(homePage.checkBannerImages(),
                "Atleast one of the Banner Images are invalid | check logs above");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerCardsClick() {
        Assert.assertTrue(homePage.checkBannerCardsClick(),
                "Atleast one the banner click/link is invalid | check logs above");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerCardsGA() {
        Assert.assertTrue(homePage.checkBannerCardsGA(),
                "Atleast one the cards GA is invalid | check logs above");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyBannerCardsNoFollow() {
        Assert.assertTrue(homePage.checkBannerCardsNoFollow(),
                "Atleast one the cards rel attribute is invalid | check logs above");
    }

    @TestFilter(executionType = ExecutionType.SANITY,
            platform = {Platforms.WEB, Platforms.MWEB})
    @Test
    public void VerifyBannerPaginationVisible() {
        Assert.assertTrue(homePage.checkBannerPaginationVisible(),
                "Banner Pagination Not Visible");
    }

    @TestFilter(executionType = ExecutionType.SANITY,
            platform = {Platforms.WEB, Platforms.MWEB})
    @Test
    public void VerifyBannerPaginationClickable() {
        Assert.assertTrue(homePage.checkBannerPaginationClickable(),
                "Atleast of the Banner Pagination Not Clickable | check logs above");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyNumberOfStoryCategories() {
        Assert.assertTrue(homePage.checkNumberOfStoryCategories(),
                "Number of Story Categories Shown are Not 7");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyStoryCategoryTitles() {
        Assert.assertTrue(homePage.checkStoryCategoryTitles(),
                "Atleast one of the category title mismatches, check logs above");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyCategoryOrder() {
        Assert.assertTrue(homePage.checkCategoryOrder(),
                "Atleast one of the category order mismatches, check logs above");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyNumberOfHighlightStoryInCategories() {
        Assert.assertTrue(homePage.checkNumberOfHighlightStoryInCategories(),
                "Atleast one category contains more than one highlight story, check logs above");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyNumberOfOtherStoriesInCategories() {
        Assert.assertTrue(homePage.checkNumberOfOtherStoriesInCategories(),
                "Atleast one category contains more than one highlight story, check logs above");
    }

    @TestFilter(executionType = ExecutionType.REGRESSION)
    @Test
    public void VerifyHighlightStories() {
        Assert.assertTrue(homePage.checkHighlightStories(),
                "Atleast one of the highlight story is invalid, check logs above");
    }

    @TestFilter(executionType = ExecutionType.REGRESSION)
    @Test
    public void VerifyOtherStories() {
        Assert.assertTrue(homePage.checkOtherStories(),
                "Atleast one of the Other story is invalid, check logs above");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyCategoryNavigateButtons() {
        Assert.assertTrue(homePage.checkCategoryNavigateButtons(),
                "Atleast one of the Navigate Button is invalid, check logs above");
    }

    @TestFilter(executionType = ExecutionType.SANITY)
    @Test
    public void VerifyContextualWidgets() {
        Assert.assertTrue(homePage.checkContextualWidgets(),
                "Atleast one of the contextual widget is invalid, check logs above");
    }
}
