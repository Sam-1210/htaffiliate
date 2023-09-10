package org.shopnow.utility;

import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TabHelper {

    private WebDriver driver;
    private final List<String> tabOrder = new ArrayList<>();
    private String lastSwitchedTabHandle = null;
    private String latestTabHandle = null;

    private static final ThreadLocal<TabHelper> TABHELPER_THREADOCAL = new InheritableThreadLocal<>();

    public static TabHelper getInstance(WebDriver driver) {
        if(TABHELPER_THREADOCAL.get() == null) {
            TABHELPER_THREADOCAL.set(new TabHelper(driver));
        } /*else if (TABHELPER_THREADOCAL.get().driver != driver) {
            // need to hash with driver not only thread
        }*/

        return TabHelper.TABHELPER_THREADOCAL.get();
    }

    private TabHelper(WebDriver driver) {
        this.driver = driver;
        tabOrder.addAll(driver.getWindowHandles());
    }

    private void open() {
        Set<String> windowHandles = driver.getWindowHandles();
        String newTabHandle = findNewTabHandle(windowHandles);

        if (newTabHandle != null) {
            tabOrder.add(newTabHandle);
            latestTabHandle = newTabHandle;
        }
    }

    public void OpenLinkInNewTab(By LinkElement) {
        driver.findElement(LinkElement).sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
        open();
        switchToLatestTab();
    }

    public void OpenLinkInNewTab(String URL) {
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", URL);
        open();
        switchToLatestTab();
    }

    public void OpenLinkInNewTab(WebElement LinkElement) {
        LinkElement.sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
        open();
        switchToLatestTab();
    }

    public void close() {
        String handle = driver.getWindowHandle();
        driver.close();
        tabOrder.remove(handle);

        if(lastSwitchedTabHandle != null && !handle.equals(lastSwitchedTabHandle)) {
            driver.switchTo().window(lastSwitchedTabHandle);
        }
        else lastSwitchedTabHandle = null;
        if(latestTabHandle.equals(handle)) latestTabHandle = null;
    }

    public void close(int tabIndex) {
        String handle = tabOrder.get(tabIndex);
        driver.switchTo().window(handle);
        driver.close();
        tabOrder.remove(tabIndex);
        if(!handle.equals(lastSwitchedTabHandle)) {
            driver.switchTo().window(lastSwitchedTabHandle);
        }
        else lastSwitchedTabHandle = null;
        if(latestTabHandle.equals(handle)) latestTabHandle = null;
    }

    public void closeLastSwitchedTab() {
        if (lastSwitchedTabHandle != null) {
            String current = driver.getWindowHandle();
            driver.switchTo().window(lastSwitchedTabHandle);
            driver.close();
            tabOrder.remove(lastSwitchedTabHandle);
            if(!current.equals(lastSwitchedTabHandle)) driver.switchTo().window(current);
            lastSwitchedTabHandle = null;
        }
    }

    public void closeLatestTab() {
        if (latestTabHandle != null) {
            String current = driver.getWindowHandle();
            driver.switchTo().window(latestTabHandle);
            driver.close();
            tabOrder.remove(latestTabHandle);
            if(!current.equals(latestTabHandle)) driver.switchTo().window(current);
            latestTabHandle = null;
        }
    }

    public void switchToTab(int tabIndex) {
        String handle = tabOrder.get(tabIndex);
        driver.switchTo().window(handle);
        lastSwitchedTabHandle = handle;
    }

    public void switchToTabWithURL(String targetURL) {
        for (String handle : tabOrder) {
            driver.switchTo().window(handle);
            if (driver.getCurrentUrl().equals(targetURL)) {
                lastSwitchedTabHandle = handle;
                return;
            }
        }
    }

    public void switchToTabWithTitle(String targetTitle) {
        for (String handle : tabOrder) {
            driver.switchTo().window(handle);
            if (driver.getTitle().equals(targetTitle)) {
                lastSwitchedTabHandle = handle;
                return;
            }
        }
    }

    public void switchToLastSwitchedTab() {
        if (lastSwitchedTabHandle != null) {
            driver.switchTo().window(lastSwitchedTabHandle);
            lastSwitchedTabHandle = null;
        }
    }

    public void switchToLatestTab() {
        if (latestTabHandle != null) {
            lastSwitchedTabHandle = driver.getWindowHandle();
            driver.switchTo().window(latestTabHandle);
        }
    }

    private String findNewTabHandle(Set<String> windowHandles) {
        for (String handle : windowHandles) {
            if (!tabOrder.contains(handle)) {
                return handle;
            }
        }
        return null;
    }
}
