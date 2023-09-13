package org.shopnow.tests;

import org.shopnow.annotations.TestDetails;
import org.shopnow.base.BasePage;
import org.shopnow.base.BaseTest;
import org.shopnow.enums.ExecutionType;
import org.shopnow.enums.POM;
import org.shopnow.enums.Platforms;
import org.shopnow.pom.components.common.CommonHeader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Header extends BaseTest {
    private CommonHeader header;

    @BeforeMethod
    public void Setup() {
        if(header == null) {
            header = (CommonHeader) BasePage.getInstanceOf(CommonHeader.class, POM.COMPONENTS);
        }
    }

    @TestDetails(testcaseID = "ATC1", platform = {Platforms.WEB, Platforms.MWEB, Platforms.AMP},
            executionType = ExecutionType.SANITY)
    @Test
    public void VerifyHeaderLogo() {
        Assert.assertTrue(header.checkHeaderLogo(),
                "Either header logo is not visible or not clickable | check logs above");
    }

    @TestDetails(testcaseID = "ATC4", platform = {Platforms.WEB, Platforms.MWEB, Platforms.AMP},
            executionType = ExecutionType.SANITY)
    @Test
    public void VerifyNumberOfStoryCategories() {
        Assert.assertTrue(header.checkNumberOfCategories(),
                "Number of Categories Shown are Not 7");
    }

    @TestDetails(testcaseID = "ATC5", platform = {Platforms.WEB, Platforms.MWEB, Platforms.AMP},
            executionType = ExecutionType.SANITY)
    @Test
    public void VerifyHeadings() {
        Assert.assertTrue(header.checkHeadings(),
                "Atleast one of the heading mismatches | check logs above");
    }

    @TestDetails(testcaseID = "ATC7", platform = {Platforms.WEB, Platforms.MWEB, Platforms.AMP},
            executionType = ExecutionType.SANITY)
    @Test
    public void VerifyNumberOfSubcategoriesInCategories() {
        Assert.assertTrue(header.checkNumberOfSubcategoriesInCategories(),
                "Atleast one category contains invalid number of subcategories | check logs above");
    }

    @TestDetails(testcaseID = "ATC8", platform = {Platforms.WEB, Platforms.MWEB, Platforms.AMP},
            executionType = ExecutionType.SANITY)
    @Test
    public void VerifySubHeadings() {
        Assert.assertTrue(header.checkSubHeadings(),
                "Atleast one of the subcategory mismatches | check logs above");
    }
}
