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
    public void VerifyNoFollowOnStories() {
        TestData testData = GetTestData();
        Assert.assertTrue(affiliateRevenue.checkNoFollow(testData),
                "Some or All Links do not use nofollow rule, check logs above");
    }

    @TestDetails(testcaseID = "T1")
    @Test
    public void VerifyNoFollowSectionPages() {
        Assert.assertTrue(affiliateRevenue.checkNoFollowSectionPages(),
                "Some or All Links do not use nofollow rule, check logs above");
    }
}
