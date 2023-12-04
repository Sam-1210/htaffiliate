package org.shopnow.tests;

import org.shopnow.annotations.TestDetails;
import org.shopnow.base.BaseTest;
import org.shopnow.pom.pages.common.CommonAffiliateRevenue;
import org.shopnow.structures.TestData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AffiliateRevenue extends BaseTest {
    private CommonAffiliateRevenue affiliateRevenue;

    @BeforeMethod
    public void Setup() {
        if(affiliateRevenue == null) {
            affiliateRevenue = new CommonAffiliateRevenue();
        }
    }

    @TestDetails(testcaseID = "T1")
    @Test
    public void VerifyNoFollowStoryPages() {
        TestData testData = GetTestData();
        Assert.assertTrue(affiliateRevenue.checkNoFollowStoryPages(testData),
                "Some or All Links do not use nofollow rule, check logs above");
    }

    @TestDetails(testcaseID = "T2")
    @Test
    public void VerifyNoFollowSectionPages() {
        TestData testData = GetTestData();
        Assert.assertTrue(affiliateRevenue.checkNoFollowSectionPages(testData),
                "Some or All Links do not use nofollow rule, check logs above");
    }

    @TestDetails(testcaseID = "T3")
    @Test
    public void VerifyTargetAttributeOnStories() {
        TestData testData = GetTestData();
        Assert.assertTrue(affiliateRevenue.checkTargetAttributeOnStories(testData),
                "Some or All Links do not have proper target value, check logs above");
    }

    @TestDetails(testcaseID = "T4")
    @Test
    public void VerifyTargetAttributeOnSectionPages() {
        TestData testData = GetTestData();
        Assert.assertTrue(affiliateRevenue.checkTargetAttributeOnSectionPages(testData),
                "Some or All Links do not have proper target value, check logs above");
    }

    @TestDetails(testcaseID = "T5")
    @Test
    public void VerifyTagsAmazonOnStories() {
        TestData testData = GetTestData();
        Assert.assertTrue(affiliateRevenue.checkTagsAmazonOnStories(testData),
                "Some or All Links do not use nofollow rule, check logs above");
    }

    @TestDetails(testcaseID = "T6")
    @Test
    public void VerifyTagsAmazonOnSectionPages() {
        TestData testData = GetTestData();
        Assert.assertTrue(affiliateRevenue.checkTagsAmazonOnSectionPages(testData),
                "Some or All Links do not use nofollow rule, check logs above");
    }

    @TestDetails(testcaseID = "T7")
    @Test
    public void VerifyTagsFlipkartOnStories() {
        TestData testData = GetTestData();
        Assert.assertTrue(affiliateRevenue.checkTagsFlipkartOnStories(testData),
                "Some or All Links do not use nofollow rule, check logs above");
    }

    @TestDetails(testcaseID = "T8")
    @Test
    public void VerifyTagsFlipkartOnSectionPages() {
        TestData testData = GetTestData();
        Assert.assertTrue(affiliateRevenue.checkTagsFlipkartOnSectionPages(testData),
                "Some or All Links do not use nofollow rule, check logs above");
    }

    @TestDetails(testcaseID = "T9")
    @Test
    public void VerifyTagsCromaOnStories() {
        TestData testData = GetTestData();
        Assert.assertTrue(affiliateRevenue.checkTagsCromaOnStories(testData),
                "Some or All Links do not use nofollow rule, check logs above");
    }

    @TestDetails(testcaseID = "T10")
    @Test
    public void VerifyTagsCromaOnSectionPages() {
        TestData testData = GetTestData();
        Assert.assertTrue(affiliateRevenue.checkTagsCromaOnSectionPages(testData),
                "Some or All Links do not use nofollow rule, check logs above");
    }
}
