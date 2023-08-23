package org.shopnow.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.shopnow.utility.Logger;


public class Sitemap {
    public static class SitemapItem {
        private final String Title;
        private final String Link;
        private ArrayList<SitemapItem> childrens;

        public SitemapItem(String Title, String Link, ArrayList<SitemapItem> childrens) {
            this.Title = Title;
            this.Link = Link;
            this.childrens = childrens;
        }

        public String getTitle() {
            return Title;
        }

        public String getLink() {
            return Link;
        }

        public ArrayList<SitemapItem> getChildrens() {
            return childrens;
        }

        private static void flattenRecursive(SitemapItem item, ArrayList<SitemapItem> flattenedList) {
            flattenedList.add(item);
            if(item.getChildrens() == null) return;
            for (SitemapItem child : item.getChildrens()) {
                flattenRecursive(child, flattenedList);
            }
        }

        public static ArrayList<SitemapItem> Flatten(SitemapItem item) {
            ArrayList<SitemapItem> flattenedList = new ArrayList<>();
            flattenRecursive(item, flattenedList);
            return flattenedList;
        }

        @Override
        public String toString() {
            StringBuilder childrensStr = new StringBuilder();
            if(childrens != null) {
                for(SitemapItem child : childrens) {
                    childrensStr.append("\t").append(child.toString());
                }
            }

            return Title + "\t" + Link + "\n" + childrensStr;
        }
    }

    private static ArrayList<SitemapItem> CheckForMultiplePages(WebDriver driver, ArrayList<SitemapItem> items) {
        ArrayList<SitemapItem> updated = new ArrayList<>();
        for(SitemapItem it : items) {
            updated.add(it);
            try {
                driver.navigate().to(it.getLink());
                List<WebElement> pagination = driver.findElements(By.cssSelector("div.dynPagination"));
                if (!pagination.isEmpty()) {
                    List<WebElement> pageNums = pagination.get(0).findElements(By.cssSelector("li"));
                    int LastPageNum = Integer.parseInt(pageNums.get(pageNums.size() - 2).getText());

                    for (int i = 2; i <= LastPageNum; i++) {
                        updated.add(new SitemapItem(it.Title, String.format("%s?page=%d", it.Link, i), null));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(it.getChildrens() != null) {
                it.childrens = CheckForMultiplePages(driver, it.getChildrens());
                //updated.addAll(CheckForMultiplePages(driver, it.getChildrens()));
            }
        }

        return updated;
    }

    public static ArrayList<SitemapItem> Get(WebDriver driver) {
        return Get(driver, "https://shopnow.hindustantimes.com", "/sitemap");
    }
    public static ArrayList<SitemapItem> Get(WebDriver driver, String Domain, String SitemapPath)
    {
        String backup = driver.getCurrentUrl();
        ArrayList<SitemapItem> sitemapItems = new ArrayList<>();

        try {
            driver.get(Domain + SitemapPath);
            List<WebElement> elems = driver.findElements(By.cssSelector("div.siteMap-section > ul"));

            for (WebElement elm : elems) {
                WebElement head = elm.findElement(By.cssSelector("li.header.sitemaplist a"));
                List<WebElement> subElms = elm.findElements(By.cssSelector("li.sitemapsublist a"));

                ArrayList<SitemapItem> childrens = new ArrayList<>();
                for (WebElement celm : subElms) {
                    childrens.add(new SitemapItem(celm.getText(), Domain + extractPath(celm.getAttribute("href")), null));
                }
                sitemapItems.add(new SitemapItem(head.getText(), Domain + extractPath(head.getAttribute("href")), childrens));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally {
            driver.get(backup);
        }

        //return CheckForMultiplePages(driver, sitemapItems);
        return sitemapItems;
    }

    public static Set<String> GetUsingXML(WebDriver driver, String XML_URL)
    {
        String backup = driver.getCurrentUrl();
        Set<String> urls = new HashSet<>();

        try {
            driver.get(XML_URL);
            List<WebElement> elms = driver.findElements(By.tagName("loc"));
            ArrayList<String> linkTexts = new ArrayList<>();
            for (WebElement elm : elms) {
                linkTexts.add(elm.getText());
            }
            for(String lt:linkTexts) {
                if(XML_URL.toLowerCase().endsWith(".xml")) {
                    urls.addAll(GetUsingXML(driver, lt));
                } else {
                    urls.add(lt);
                }
            }
        } catch (Exception e) {
            Logger.Trace("Exception Occurred | message: " + e);
        }
        finally {
            driver.get(backup);
        }

        return urls;
    }

    public static String extractPath(String url) {
        String regex = "https?://[^/]+(/.*)";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }
}
