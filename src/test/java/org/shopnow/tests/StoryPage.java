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
                "Either follow us text is not shown or wrong, check logs");
    }

    @TestDetails(testcaseID = "T8", executionType = ExecutionType.SANITY)
    @Test(dataProvider = "StoryProvider")
    public void VerifySocialMediaIconLoadedInFollowUs(TestData testData) {
        Assert.assertTrue(storyPage.checkSocialMediaIconLoadedInFollowUs(testData),
                "Either follow us text is not shown or wrong, check logs");
    }
}
