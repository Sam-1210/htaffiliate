package org.shopnow.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.shopnow.enums.POM;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.utility.DriverManager;
import org.shopnow.utility.Logger;
import org.shopnow.utility.TabHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BasePage {
    protected static final ApplicationProperties applicationProperties = ApplicationProperties.getInstance();
    protected WebDriver driver;
    protected TabHelper tabHelper;
    public BasePage() {
        driver = DriverManager.getInstance().getDriver();
        driver.get(ApplicationProperties.getInstance().getEnvironment().getURL());
        tabHelper = TabHelper.getInstance(driver);
        PageFactory.initElements(driver, this);
    }

    public static Object getInstanceOf(Class<?> clazz, POM pom, Object... initArgs) {
        return getClassInstance(clazz, pom, initArgs);
    }

    private static Object getClassInstance(Class<?> clazz, POM pom, Object... initArgs) {
        String pageClassName = clazz.getSimpleName();
        if (pageClassName.toLowerCase().startsWith("common"))
            pageClassName = pageClassName.substring(6).trim();
        else if (pageClassName.toLowerCase().startsWith("abstract"))
            pageClassName = pageClassName.substring(8).trim();

        pageClassName = switch (ApplicationProperties.getInstance().getPlatform()) {
            case WEB -> "org.shopnow.pom." + pom.getName() + ".web." + pageClassName;
            case MWEB -> "org.shopnow.pom." + pom.getName() + ".mweb." + pageClassName;
            case AMP -> "org.shopnow.pom." + pom.getName() + ".amp." + pageClassName;
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

    public static Object getPageClassInstance(Class<?> clazz, Object... initArgs) {
        return getClassInstance(clazz, POM.PAGES, initArgs);
    }

    public static Object getComponentClassInstance(Class<?> clazz, Object... initArgs) {
        return getClassInstance(clazz, POM.COMPONENTS, initArgs);
    }

    public boolean runAll() {
        boolean overallResult = true;
        Class<?> clazz = this.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("check")) {
                try {
                    boolean methodResult = (boolean) method.invoke(this);
                    overallResult &= methodResult;
                } catch (Exception e) {
                    Logger.Except(e);
                    overallResult = false;
                }
            }
        }
        return overallResult;
    }
}
