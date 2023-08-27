package org.shopnow.pom.pages.common;

import lombok.Getter;
import org.shopnow.base.BasePage;
import org.shopnow.enums.Environment;
import org.shopnow.structures.ApplicationProperties;

public class CommonHome extends BasePage {
    @Getter
    protected final String URL = ApplicationProperties.getInstance().getEnvironment().getURL();

    public boolean checkNumberOfStoryCategories() { return true; }
    public boolean checkStoryCategoryTitles() { return true; }

    public boolean checkNumberOfHighlightStoryInCategories() { return true; }

    public boolean checkNumberOfOtherStoriesInCategories() { return true; }

    public boolean checkHighlightStories() { return true; }

    public boolean checkOtherStories() { return true; }

    public boolean checkCategoryNavigateButtons() { return true; }
}
