package org.shopnow.pom;

import org.openqa.selenium.By;

public abstract class Template /*extends BasePage*/ {
    /**
     * naming conventions for locators
     * List* returns list
     * _* must be used relative to parent
     * el* defines the variable is a WebElement
     */

    /**
     * locators | common for all platforms
     */
    // absolute
    protected final By AboluteLocator = By.cssSelector("");
    // relative
    protected final By RelativeLocator = By.cssSelector("");

    /**
     * mandatory test | Platform Specifics
     * make abstract
     */
    public abstract boolean MandotoryTest();

    /**
     * optional tests | Platform Specific
     * provide body returning true
     */
    public boolean OptionalTest() { return true; }

    /**
     * default, common tests for all
     */
    public boolean CommonTest() {
        return true;
    }
}
