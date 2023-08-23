package org.shopnow.tests;

import org.shopnow.annotations.TestFilter;
import org.shopnow.base.BasePage;
import org.shopnow.base.BaseTest;
import org.shopnow.pages.common.CommonHome;
import org.shopnow.utility.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePage extends BaseTest {
    private CommonHome homePage;

    @BeforeMethod
    public void init() {
        if(homePage == null) {
            homePage = (CommonHome) BasePage.getInstanceOf(CommonHome.class);
        }
    }

    @TestFilter(platform = {"web"})
    @Test
    public void VerifyTest() {
        DriverManager.getInstance().getDriver().get("https://shopnow.hindustantimes.com/");
        Assert.assertTrue(true, "Running now");
    }
}
