package org.shopnow.utility;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.shopnow.enums.PseudoSelector;
import org.shopnow.structures.ApplicationProperties;

import java.time.Duration;

public class DriverHelper {
    public static void NavigateTo(WebDriver driver, String URI) {
        String finalURL = ApplicationProperties.getInstance().getEnvironment().getURL() + "/" + URI;
        if(URI.charAt(0) == '/') finalURL = ApplicationProperties.getInstance().getEnvironment().getURL() + URI;
        Logger.Log("Navigating to %s", finalURL);
        driver.get(finalURL);
    }
    public static void ScrollWithJS(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void ScrollWithJS(WebDriver driver, By element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(element));
    }

    public static void ClickWithJS(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static void ClickWithJS(WebDriver driver, By element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(element));
    }

    public static void ScrollBy(WebDriver driver, int x, int y) {
        ((JavascriptExecutor) driver).executeScript(String.format("window.scrollBy(%d,%d)", x, y));
    }

    public static void ScrollToBottom(WebDriver driver, int x, int y) {
        ((JavascriptExecutor) driver).executeScript(String.format("window.scrollBy(0,document.body.scrollHeight)", x, y));
    }

    public static void MouseOver(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).perform();
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

    // misc
    public String GetContentOfPseudoElement(WebDriver driver, String css, PseudoSelector psuedoSelector) {
        return ((JavascriptExecutor) driver).executeScript(
                        String.format("return window.getComputedStyle(document.querySelector('%s'),'%s').getPropertyValue('content')",
                                css, psuedoSelector.name()))
                .toString();
    }
}
