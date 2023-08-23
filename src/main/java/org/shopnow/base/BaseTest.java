package org.shopnow.base;

import org.shopnow.enums.*;
import org.shopnow.enums.SupportedBrowsers;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.utility.DriverManager;
import org.shopnow.utility.Logger;
import org.testng.*;
import org.testng.annotations.*;

@Listeners({org.testng.reporters.XMLReporter.class})
public class BaseTest {
    protected final ApplicationProperties applicationProperties = ApplicationProperties.getInstance();

    @BeforeSuite
    @Parameters({
            "environment",
            "executionType",
            "platformName",
            "browser",
            "browserMode",
            "isBrowserStack",
            "isAppium",
            "capabilitiesJson",
            "buildNumber",
            "googleSheetFlag"
    })
    protected void initApplication(ITestContext testContext,
                                   @Optional(value = "prod") String environment,
                                   @Optional(value = "sanity") String executionType,
                                   @Optional(value = "web") String platformName,
                                   @Optional(value = "chrome") String browser,
                                   @Optional(value = "normal") String browserMode,
                                   @Optional(value = "false") String isBrowserStack,
                                   @Optional(value = "false") String isAppium,
                                   @Optional(value = "windows10_chrome") String capabilitiesJson,
                                   @Optional(value = "1221") String buildNumber,
                                   @Optional(value = "false") String googleSheetFlag) {
        try {
            Logger.Heading("Initializing Application");
            applicationProperties.setEnvironment(Environment.valueOf(environment.toUpperCase()));
            applicationProperties.setExecutionType(ExecutionType.valueOf(executionType.toUpperCase()));
            applicationProperties.setPlatform(Platforms.valueOf(platformName.toUpperCase()));
            applicationProperties.setBrowser(SupportedBrowsers.valueOf(browser.toUpperCase()));
            applicationProperties.setBrowserMode(BrowserMode.valueOf(browserMode.toUpperCase()));
            applicationProperties.setBrowserStack(Boolean.parseBoolean(isBrowserStack));
            applicationProperties.setAppium(Boolean.parseBoolean(isAppium));
            applicationProperties.setCapabilitiesJsonFileName(capabilitiesJson);
            applicationProperties.setGoogleSheet(Boolean.parseBoolean(googleSheetFlag));
            Logger.Log("Initialized Application, Details:");
            Logger.Log("Environment: " + applicationProperties.getEnvironment().name());
            Logger.Log("Execution Type: " + applicationProperties.getExecutionType().name());
            Logger.Log("Platform: " + applicationProperties.getPlatform().name());
            Logger.Log("Browser Mode: " + applicationProperties.getBrowserMode().name());
            Logger.Log("Browser Stack Flag: " + applicationProperties.isBrowserStack());
            Logger.Log("Appium Flag: " + applicationProperties.isAppium());
            Logger.Log("Capabilities Json: " + applicationProperties.getCapabilitiesJsonFileName());
            Logger.Log("Google Sheet Flag: " + applicationProperties.isGoogleSheet());
            Logger.Log("Build Number: " + buildNumber);
        } catch (Exception e) {
            Logger.Except(e);
        }
    }

    @AfterSuite
    public void tearDown() {
        DriverManager.QuitAll();
    }
}
