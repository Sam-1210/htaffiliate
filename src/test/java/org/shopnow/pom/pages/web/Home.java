package org.shopnow.pom.pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.shopnow.pom.pages.common.CommonHome;
import org.shopnow.utility.Logger;

import java.util.List;

public class Home extends CommonHome {
    @FindBy(css="div.swiperbanner.swiper-container-horizontal > div.swiper-wrapper")
    private WebElement SwiperCardContainer;

    @FindBy(css="div.swiperbanner.swiper-container-horizontal > div.swiper-wrapper div.swiper-slide")
    private List<WebElement> SwiperCards;

    @FindBy(css="div.swiperbanner.swiper-container-horizontal > div.swiper-pagination.swiper-pagination-clickable.swiper-pagination-bullets span")
    private List<WebElement> SwiperPagination;

    @FindBy(css="div.productLeftSection")
    private WebElement MainContainer; // have: div.listofproduct | div.productbtnbar | section.ofersliderbox

    @FindBy(css="div.listofproduct")
    private List<WebElement> StoryCategoryContainer;
    @FindBy(css="div.productbtnbar")
    private List<WebElement> CategoryNavigateButton;
    @FindBy(css="section.ofersliderbox > div.offerSlideRow")
    private List<WebElement> CategoryContextualWidgets;
    private final By FirstStoryHeading = By.cssSelector("div.listProHeading.largeHeading > h1 > span > a");
    private final By OtherStoriesHeadings = By.cssSelector("div.listProHeading.largeHeading > h2 > span > a");
    private final By HighlightStory = By.cssSelector("section.productMainbox > div.productsCols:nth-child(1) > article.productFeatureBox");
    private final By HighlightTitle = By.cssSelector("div.featureTitle > a");
    private final By HighlightImg = By.cssSelector("div.proFeatureImg > img");
    private final By PublishDate = By.cssSelector("span.publishedText");
    private final By OtherStories = By.cssSelector("section.productMainbox > div.productsCols:nth-child(2) > article.proArticle");
    private final By OtherStoryTitle = By.cssSelector("div.proArticleWrap > a > div.proArticleRight > div.featureTitle2");
    private final By OtherStoryImg = By.cssSelector("div.artImgBox > img");

    @Override
    public boolean checkNumberOfStoryCategories() {
        return StoryCategoryContainer.size() == 7;

    }

    @Override
    public boolean checkStoryCategoryTitles() {
        boolean result = true;
        String[] titles = {
                "BEST ONLINE DEALS AND OFFERS",
                "FASHION DEALS", "ELECTRONICS DEALS",
                "HEALTH AND BEAUTY DEALS", "PET CARE AND SUPPLIES DEALS",
                "HOME AND KITCHEN DEALS", "AMAZON SALE DEALS"
        };
        String firstTitle = StoryCategoryContainer.get(0).findElement(FirstStoryHeading).getText();
        result &= firstTitle.equals(titles[0]);
        if(!result) {
            Logger.Trace("Heading Mismatch Expected: %s, Actual: %s", titles[0], firstTitle);
        }
        for(int i = 1; i < StoryCategoryContainer.size(); i++) {
            String actual = StoryCategoryContainer.get(i).findElement(OtherStoriesHeadings).getText();
            boolean match = actual.equals(titles[i]);
            if(!match) {
                Logger.Trace("Heading Mismatch Expected: %s, Actual: %s", titles[i], actual);
            }
            result &= match;
        }

        return result;
    }

    @Override
    public boolean checkNumberOfHighlightStoryInCategories() {
        boolean result = true;
        int expected = 1;
        int firstCount = StoryCategoryContainer.get(0).findElements(HighlightStory).size();
        result &= firstCount == expected;
        if(!result) Logger.Trace("Highlight Story Mismatch for category [%s] | Expected: %s, Actual: %s",
                StoryCategoryContainer.get(0).findElement(FirstStoryHeading).getText(),
                expected, firstCount);
        for(int i = 1; i < StoryCategoryContainer.size(); i++) {
            int highlightStoryCount = StoryCategoryContainer.get(i).findElements(HighlightStory).size();
            boolean match = highlightStoryCount == expected;
            if(!match) Logger.Trace("Highlight Story Mismatch for category [%s] | Expected: %s, Actual: %s",
                    StoryCategoryContainer.get(i).findElement(OtherStoriesHeadings).getText(),
                    expected, highlightStoryCount);
            result &= match;
        }

        return result;
    }

    @Override
    public boolean checkNumberOfOtherStoriesInCategories() {
        boolean result = true;
        int expected = 3;
        int firstCount = StoryCategoryContainer.get(0).findElements(OtherStories).size();
        result &= firstCount == expected;
        if(!result) Logger.Trace("Story Count Mismatch for category [%s] | Expected: %s, Actual: %s",
                StoryCategoryContainer.get(0).findElement(FirstStoryHeading).getText(),
                expected, firstCount);
        for(int i = 1; i < StoryCategoryContainer.size(); i++) {
            int highlightStoryCount = StoryCategoryContainer.get(i).findElements(OtherStories).size();
            boolean match = highlightStoryCount == expected;
            if(!match) Logger.Trace("Story Count Mismatch for category [%s] | Expected: %s, Actual: %s",
                    StoryCategoryContainer.get(i).findElement(OtherStoriesHeadings).getText(),
                    expected, highlightStoryCount);
            result &= match;
        }

        return result;
    }

    @Override
    public boolean checkHighlightStories() {
        return true;
    }
}
