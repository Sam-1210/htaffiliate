package org.shopnow.tests;

import org.shopnow.base.BasePage;
import org.shopnow.enums.POM;
import org.shopnow.pom.pages.common.CommonFashion;
import org.testng.annotations.BeforeMethod;

public class FashionPage extends CommonCategoryPages{
    private CommonFashion fashionPage;

    @Override
    @BeforeMethod
    public void Setup() {
        if(categoryPage == null) {
            fashionPage = (CommonFashion) BasePage.getInstanceOf(CommonFashion.class, POM.PAGES);
            categoryPage = fashionPage;
        }
    }

}
