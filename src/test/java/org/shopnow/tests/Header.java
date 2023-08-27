package org.shopnow.tests;

import org.shopnow.annotations.TestFilter;
import org.shopnow.base.BasePage;
import org.shopnow.base.BaseTest;
import org.shopnow.enums.POM;
import org.shopnow.pom.components.common.CommonHeader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Header extends BaseTest {
    private CommonHeader header;

    @BeforeMethod
    public void init() {
        if(header == null) {
            header = (CommonHeader) BasePage.getInstanceOf(CommonHeader.class, POM.COMPONENTS);
        }
    }

    @TestFilter(platform = {"web", "mweb", "amp"})
    @Test
    public void VerifyNumberOfStoryCategories() {
        Assert.assertTrue(header.checkNumberOfCategories(),
                "Number of Categories Shown are Not 7");
    }

    @TestFilter(platform = {"web", "mweb", "amp"})
    @Test
    public void VerifyNumberOfSubcategoriesInCategories() {
        Assert.assertTrue(header.checkNumberOfSubcategoriesInCategories(),
                "Atleast one category contains invalid number of subcategories, check logs above");
    }
}
