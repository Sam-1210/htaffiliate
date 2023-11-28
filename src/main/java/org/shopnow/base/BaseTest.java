package org.shopnow.base;

import org.apache.poi.ss.formula.functions.T;
import org.shopnow.annotations.TestDetails;
import org.shopnow.enums.*;
import org.shopnow.enums.SupportedBrowsers;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.structures.TestData;
import org.shopnow.utility.DriverManager;
import org.shopnow.utility.Logger;
import org.shopnow.utility.TestDataManager;
import org.testng.*;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Listeners({org.testng.reporters.XMLReporter.class})
public class BaseTest implements IHookable {
    protected final ApplicationProperties applicationProperties = ApplicationProperties.getInstance();
    protected TestDataManager testDataManager;
    protected Map<String, String> testResults = new HashMap<>();

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

    @BeforeClass
    public void SetupClass() {
        testDataManager = new TestDataManager(this.getClass());
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        Logger.Heading("Running:  " + testResult.getName());
        callBack.runTestMethod(testResult);
        Logger.Heading("Finished: " + testResult.getName());
    }

    @AfterMethod
    public void AfterTest(ITestResult result) {
        try {
            String methodName = result.getMethod().getMethodName();
            String status = "";

            switch (result.getStatus()) {
                case ITestResult.SUCCESS:
                    status = "PASSED";
                    break;
                case ITestResult.FAILURE:
                    status = "FAILED";
                    break;
                case ITestResult.SKIP:
                    status = "SKIPPED";
                    break;
            };

            testResults.put(methodName, status);
        } catch (Exception e) {
            Logger.Except(e);
        }
    }

    @AfterClass
    public void AfterClass() {
        testDataManager.WriteResults(testResults);
    }

    @AfterSuite
    public void tearDown() {
        DriverManager.QuitAll();
    }

    protected String GetTestDataAsString(String MethodName) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length >= 3) {
            StackTraceElement testMethodElement = stackTrace[2];
            try {
                String methodName = testMethodElement.getMethodName();
                return testDataManager.GetDataForTestCase(methodName);
            } catch (Exception e) {
                Logger.Except(e);
            }
        }

        return null;
    }

    protected TestData GetTestData() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length >= 3) {
            StackTraceElement testMethodElement = stackTrace[2];
            try {
                String methodName = testMethodElement.getMethodName();
                return new TestData(testDataManager.GetDataForTestCase(methodName));
            } catch (Exception e) {
                Logger.Except(e);
            }
        }

        return new TestData("");
    }
}
