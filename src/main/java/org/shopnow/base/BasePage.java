package org.shopnow.base;

import org.openqa.selenium.WebDriver;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.utility.DriverManager;
import org.shopnow.utility.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BasePage {
    protected static final ApplicationProperties applicationProperties = ApplicationProperties.getInstance();
    protected WebDriver driver;
    public BasePage() {
        driver = DriverManager.getInstance().getDriver();
    }

    public static Object getInstanceOf(Class<?> clazz, Object... initArgs) {
        return getPageClassInstance(clazz, initArgs);
    }

    private static Object getPageClassInstance(Class<?> clazz, Object... initArgs) {
        String pageClassName = clazz.getSimpleName();
        if (pageClassName.toLowerCase().startsWith("common"))
            pageClassName = pageClassName.substring(6).trim();
        else if (pageClassName.toLowerCase().startsWith("abstract"))
            pageClassName = pageClassName.substring(8).trim();

        pageClassName = switch (ApplicationProperties.getInstance().getPlatform()) {
            case WEB -> "org.shopnow.pages.web." + pageClassName;
            case MWEB -> "org.shopnow.pages.mweb." + pageClassName;
            case AMP -> "org.shopnow.pages.amp." + pageClassName;
        };

        try {
            Class<?> var0 = Class.forName(pageClassName);
            Constructor<?> constructor = var0.getConstructors()[0];
            return constructor.newInstance(initArgs);
        } catch (ClassNotFoundException e) {
            Logger.Error("Unable to find class [" + pageClassName + "] :: " + e.getMessage());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Logger.Error("Unable to instantiate class [" + pageClassName + "] :: " + e.getMessage());
        }
        return null;
    }
}
