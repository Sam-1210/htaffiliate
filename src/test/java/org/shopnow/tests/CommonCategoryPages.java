package org.shopnow.tests;

import org.shopnow.annotations.TestDetails;
import org.shopnow.base.BaseTest;
import org.shopnow.enums.ExecutionType;
import org.shopnow.pom.pages.common.BaseCategory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public abstract class CommonCategoryPages extends BaseTest {
    protected BaseCategory categoryPage;

    /**
     * requires the category page implementation to setup
     * @property categoryPage
     */
    @BeforeMethod
    public abstract void Setup();

    @TestDetails(testcaseID = "ATC1", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyNumberOfStoryCategories() {
        Assert.assertTrue(categoryPage.checkNumberOfStoryCategories(),
                "Number of Story Categories Shown are Not 7");
    }

    @TestDetails(testcaseID = "ATC1", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyStoryCategoryTitles() {
        Assert.assertTrue(categoryPage.checkStoryCategoryTitles(),
                "Atleast one of the category title mismatches, check logs above");
    }

    @TestDetails(testcaseID = "ATC1", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyCategoryOrder() {
        Assert.assertTrue(categoryPage.checkCategoryOrder(),
                "Atleast one of the category order mismatches, check logs above");
    }

    @TestDetails(testcaseID = "ATC1", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyNumberOfHighlightStoryInCategories() {
        Assert.assertTrue(categoryPage.checkNumberOfHighlightStoryInCategories(),
                "Atleast one category contains more than one highlight story, check logs above");
    }

    @TestDetails(testcaseID = "ATC1", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyNumberOfOtherStoriesInCategories() {
        Assert.assertTrue(categoryPage.checkNumberOfOtherStoriesInCategories(),
                "Atleast one category contains more than one highlight story, check logs above");
    }

    @TestDetails(testcaseID = "ATC1", executionType = ExecutionType.REGRESSION)
    @Test
    public void VerifyHighlightStories() {
        Assert.assertTrue(categoryPage.checkHighlightStories(),
                "Atleast one of the highlight story is invalid, check logs above");
    }

    @TestDetails(testcaseID = "ATC1", executionType = ExecutionType.REGRESSION)
    @Test
    public void VerifyOtherStories() {
        Assert.assertTrue(categoryPage.checkOtherStories(),
                "Atleast one of the Other story is invalid, check logs above");
    }

    @TestDetails(testcaseID = "ATC1", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyCategoryNavigateButtons() {
        Assert.assertTrue(categoryPage.checkCategoryNavigateButtons(),
                "Atleast one of the Navigate Button is invalid, check logs above");
    }

    @TestDetails(testcaseID = "ATC1", executionType = ExecutionType.SANITY)
    @Test
    public void VerifyContextualWidgets() {
        Assert.assertTrue(categoryPage.checkContextualWidgets(),
                "Atleast one of the contextual widget is invalid, check logs above");
    }
}
