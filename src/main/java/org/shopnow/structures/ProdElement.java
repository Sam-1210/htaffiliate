package org.shopnow.structures;

import org.apache.commons.math3.genetics.Fitness;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ProdElement {
    private final String locator;
    private final ArrayList<Filter> filters;

    public ProdElement(String locator, ArrayList<Filter> filters) {
        this.locator = locator;
        this.filters = filters;
    }

    public String getLocator() {
        return locator;
    }
    public ArrayList<Filter> getFilters() {
        return filters;
    }

    public static ArrayList<ProdElement> Get(WebDriver driver, String xLoc, ArrayList<Filter> filters) {
        ArrayList<ProdElement> elms = new ArrayList<>();

        int i = 0;
        boolean more = true;
        do {
            try {
                String xpath = String.format("(%s)[%d]", xLoc, i + 1);
                List<WebElement> elm = driver.findElements(By.xpath(xpath));
                if (!elm.isEmpty()) {
                    elms.add(new ProdElement(xpath, filters));
                } else {
                    more = false;
                }
            } catch (Exception e) {
                more = false;
            }
            i++;
        } while (more);

        return elms;
    }
}