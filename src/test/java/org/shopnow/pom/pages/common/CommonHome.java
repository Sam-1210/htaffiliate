package org.shopnow.pom.pages.common;

import org.shopnow.base.BasePage;

public class CommonHome extends BasePage {
    public boolean checkNumberOfStoryCategories() { return true; }
    public boolean checkStoryCategoryTitles() { return true; }

    public boolean checkNumberOfHighlightStoryInCategories() { return true; }

    public boolean checkNumberOfOtherStoriesInCategories() { return true; }

    public boolean checkHighlightStories() { return true; }

}
