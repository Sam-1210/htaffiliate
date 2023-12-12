package org.shopnow.tests;

import org.shopnow.annotations.TestDetails;
import org.shopnow.base.BasePage;
import org.shopnow.base.BaseTest;
import org.shopnow.enums.ExecutionType;
import org.shopnow.enums.POM;
import org.shopnow.pom.pages.common.CommonHome;
import org.shopnow.pom.pages.common.CommonStory;
import org.shopnow.structures.TestData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class StoryPage extends BaseTest {
    private CommonStory storyPage;

    @BeforeMethod
    public void Setup() {
        if(storyPage == null) {
            storyPage = (CommonStory) BasePage.getInstanceOf(CommonStory.class, POM.PAGES);
        }
    }

    @DataProvider(name = "StoryProvider")
    public Object[][] StoryProvider() {
        TestData testData = new TestData("{\"uri\":\"electronics/gadgets/top-10-casio-watches-for-2023-with-timeless-style-201683609269436.html\", \"productCount\":1, \"isProd\":true}");

        // only add stories whose env matches test env

        return new Object[][] {
                new Object[]{testData}
        };
    }

    @TestDetails(testcaseID = "T1", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyTitleVisibility(TestData testData) {
        Assert.assertTrue(storyPage.checkTitleVisibility(testData),
                "Story title is not visible or is not h1, check logs above");
    }

    @TestDetails(testcaseID = "T2", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyStoryTitle(TestData testData) {
        Assert.assertTrue(storyPage.checkStoryTitle(testData),
                "Story Title does not match with page title");
    }

    @TestDetails(testcaseID = "T3", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyPublishDate(TestData testData) {
        Assert.assertTrue(storyPage.checkPublishDate(testData),
                "Either publish date is not visible or it is in wrong format, check logs above.");
    }

    @TestDetails(testcaseID = "T4", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyAuthorImageLoaded(TestData testData) {
        Assert.assertTrue(storyPage.checkAuthorImageLoaded(testData),
                "Author image is not loaded");
    }

    @TestDetails(testcaseID = "T5", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyAuthorName(TestData testData) {
        Assert.assertTrue(storyPage.checkAuthorName(testData),
                "Author name is not visible or wrong format, check logs");
    }

    @TestDetails(testcaseID = "T6", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyFollowUsText(TestData testData) {
        Assert.assertTrue(storyPage.checkFollowUsText(testData),
                "Either follow us text is not shown or wrong, check logs");
    }

    @TestDetails(testcaseID = "T7", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifySocialMediaCountInFollowUs(TestData testData) {
        Assert.assertTrue(storyPage.checkSocialMediaCountInFollowUs(testData),
                "Some social media icons are not shown, check logs");
    }

    @TestDetails(testcaseID = "T8", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifySocialMediaIconLoadedInFollowUs(TestData testData) {
        Assert.assertTrue(storyPage.checkSocialMediaIconLoadedInFollowUs(testData),
                "Some social media icons failed to load, check logs");
    }

    @TestDetails(testcaseID = "T9", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifySocialMediaURLs(TestData testData) {
        Assert.assertTrue(storyPage.checkSocialMediaURLs(testData),
                "Some social media urls are invalid, check logs");
    }

    @TestDetails(testcaseID = "T10", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifySocialMediaAlt(TestData testData) {
        Assert.assertTrue(storyPage.checkSocialMediaAlt(testData),
                "Some social media alt is wrong, check logs");
    }

    @TestDetails(testcaseID = "T11", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyShareIconLoaded(TestData testData) {
        Assert.assertTrue(storyPage.checkShareIconLoaded(testData),
                "Share Icon is not loaded");
    }
    @TestDetails(testcaseID = "T12", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyShareMenuOpenClose(TestData testData) {
        Assert.assertTrue(storyPage.checkShareMenuOpenClose(testData),
                "Share Menu Not working, check logs");
    }

    @TestDetails(testcaseID = "T13", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyShareViaText(TestData testData) {
        Assert.assertTrue(storyPage.checkShareViaText(testData),
                "Either share via text is not shown or wrong, check logs");
    }

    @TestDetails(testcaseID = "T14", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyShareOptionCount(TestData testData) {
        Assert.assertTrue(storyPage.checkShareOptionCount(testData),
                "Some share option icons are not shown, check logs");
    }

    @TestDetails(testcaseID = "T15", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyShareOptionsIconLoaded(TestData testData) {
        Assert.assertTrue(storyPage.checkShareOptionsIconLoaded(testData),
                "Some share option icons failed to load, check logs");
    }

    @TestDetails(testcaseID = "T16", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyShareOptionsURLs(TestData testData) {
        Assert.assertTrue(storyPage.checkShareOptionsURLs(testData),
                "Some share option urls are invalid, check logs");
    }

    @TestDetails(testcaseID = "T17", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyShareOptionsAlt(TestData testData) {
        Assert.assertTrue(storyPage.checkShareOptionsAlt(testData),
                "Some share option alt is wrong, check logs");
    }

    @TestDetails(testcaseID = "T18", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyStoryBannerLoaded(TestData testData) {
        Assert.assertTrue(storyPage.checkStoryBannerLoaded(testData),
                "Share Icon is not loaded");
    }

    @TestDetails(testcaseID = "T19", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifySummaryTitleAndParagraph(TestData testData) {
        Assert.assertTrue(storyPage.checkSummaryTitleAndParagraph(testData),
                "Some sub-tests failed, check logs");
    }

    @TestDetails(testcaseID = "T20", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifySummaryExpand(TestData testData) {
        Assert.assertTrue(storyPage.checkSummaryExpand(testData),
                "Some sub-tests failed, check logs");
    }

    @TestDetails(testcaseID = "T21", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifySummaryCollapse(TestData testData) {
        Assert.assertTrue(storyPage.checkSummaryCollapse(testData),
                "Some sub-tests failed, check logs");
    }

    @TestDetails(testcaseID = "T22", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyProductPriceTableRowCount(TestData testData) {
        Assert.assertTrue(storyPage.checkProductPriceTableRowCount(testData),
                "Count Mismatch, check logs");
    }

    @TestDetails(testcaseID = "T23", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyProductPriceTableProductLinks(TestData testData) {
        Assert.assertTrue(storyPage.checkProductPriceTableProductLinks(testData),
                "Some links are invalid, check logs");
    }

    @TestDetails(testcaseID = "T24", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyProductPriceTablePriceFormat(TestData testData) {
        Assert.assertTrue(storyPage.checkProductPriceTablePriceFormat(testData),
                "Some products show wrong format for price, check logs");
    }

    @TestDetails(testcaseID = "T25", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyProductCarouselVisibleAndTitle(TestData testData) {
        Assert.assertTrue(storyPage.checkProductCarouselVisibleAndTitle(testData),
                "Some sub-tests failed, check logs");
    }

    @TestDetails(testcaseID = "T26", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyProductCarouselProdCount(TestData testData) {
        Assert.assertTrue(storyPage.checkProductCarouselProdCount(testData),
                "Count Mismatch, check logs");
    }

    @TestDetails(testcaseID = "T27", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifyProdCarouselSwipe(TestData testData) {
        Assert.assertTrue(storyPage.checkProdCarouselSwipe(testData),
                "Some sub-tests failed, check logs");
    }
}
