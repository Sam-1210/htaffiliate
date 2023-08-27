package org.shopnow.utility;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverHelper {
    public static void ScrollWithJS(WebDriver driver, WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void ScrollWithJS(WebDriver driver, By element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(element));
    }

    public static void ScrollBy(WebDriver driver, int x, int y) {
        ((JavascriptExecutor)driver).executeScript(String.format("window.scrollBy(%d,%d)", x, y));
    }

    public static void ScrollToBottom(WebDriver driver, int x, int y) {
        ((JavascriptExecutor)driver).executeScript(String.format("window.scrollBy(0,document.body.scrollHeight)", x, y));
    }

    public static void MouseOver(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    public static void OpenLinkInNewTab(WebElement LinkElement) {
        LinkElement.sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
    }

    public static void OpenLinkInNewTab(WebDriver driver, By LinkElement) {
        driver.findElement(LinkElement).sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
    }

    public static WebElement ExplicitWaitForVisibility(WebDriver driver, Duration duration, WebElement element) {
        return new WebDriverWait(driver, duration).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement ExplicitWaitForVisibility(WebDriver driver, Duration duration, By element) {
        return new WebDriverWait(driver, duration).until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public static WebElement ExplicitWaitForClickable(WebDriver driver, Duration duration, WebElement element) {
        return new WebDriverWait(driver, duration).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement ExplicitWaitForClickable(WebDriver driver, Duration duration, By element) {
        return new WebDriverWait(driver, duration).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement ExplicitWaitForPageLoad(WebDriver driver, Duration duration) {
        return new WebDriverWait(driver, duration).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
    }

    public static Boolean ExplicitWaitForURL(WebDriver driver, Duration duration, String URL) {
        return new WebDriverWait(driver, duration).until(ExpectedConditions.urlToBe(URL));
    }
}
