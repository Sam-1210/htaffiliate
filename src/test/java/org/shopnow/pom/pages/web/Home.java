package org.shopnow.pom.pages.web;

import com.beust.ah.A;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.shopnow.pom.pages.common.CommonHome;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.utility.DriverHelper;
import org.shopnow.utility.Logger;
import org.shopnow.utility.TabHelper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends CommonHome {
    protected final By BannerPaginationContainer = By.cssSelector("div.swiperbanner.swiper-container-horizontal > div.swiper-pagination.swiper-pagination-clickable.swiper-pagination-bullets");
    protected final By ListBannerPaginationBullet = By.cssSelector("div.swiperbanner.swiper-container-horizontal > div.swiper-pagination.swiper-pagination-clickable.swiper-pagination-bullets span");

    @Override
    public boolean checkBannerPaginationVisible() {
        boolean result = true;

        try {
            WebElement elPagination = driver.findElement(BannerPaginationContainer);
            result = elPagination.isDisplayed();
        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }

    @Override
    public boolean checkBannerPaginationClickable() {
        boolean result = true;

        try {

        } catch (Exception e) {
            Logger.Except(e);
            result = false;
        }

        return result;
    }
}
