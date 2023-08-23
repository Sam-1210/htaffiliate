package org.shopnow.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v112.network.Network;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.shopnow.utility.Sitemap;
import org.shopnow.structures.Filter;
import org.shopnow.structures.ProdElement;
import org.shopnow.utility.GoogleSheetUtil;
import org.shopnow.utility.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.Optional;
import java.util.logging.Level;

import org.shopnow.utility.NetworkUtils;


public class GAValidation {
    private static final ThreadLocal<WebDriver> WEBDRIVER_THREADLOCAL = new ThreadLocal<>();
    private static final int RequestWaitTime = 250;
    private String browserMode;
    private String localTestData, localResults;
    private boolean googleSheetFlag, XMLFlag;
    private String spreadsheetIDTestdata, spreadsheetIDResults;
    private String XML_URL;
    private static final List<List<Object>> Results = new ArrayList<>();

    @BeforeSuite
    @Parameters({
            "browserMode",
            "localTestData",
            "localResults",
            "googleSheetFlag",
            "spreadsheetIDTestdata",
            "spreadsheetIDResults",
            "XMLFlag",
            "XML_URL"
    }) void initApplication(ITestContext testContext,
                                       @org.testng.annotations.Optional(value = "normal") String browserMode,
                                       @org.testng.annotations.Optional(value = "GATestData.xlsx") String localTestData,
                                       @org.testng.annotations.Optional(value = "GATestResults.xlsx") String localResults,
                                       @org.testng.annotations.Optional(value = "false") String googleSheetFlag,
                                       @org.testng.annotations.Optional(value = "") String spreadsheetIDTestdata,
                                       @org.testng.annotations.Optional(value = "") String spreadsheetIDResults,
                                        @org.testng.annotations.Optional(value = "") String XMLFlag,
                                        @org.testng.annotations.Optional(value = "") String XML_URL) {
        Logger.Heading("GAValidation Suite");
        try{
            this.browserMode = browserMode;
            this.localTestData = localTestData;
            this.localResults = localResults;
            this.googleSheetFlag = Boolean.parseBoolean(googleSheetFlag);
            this.spreadsheetIDTestdata = spreadsheetIDTestdata;
            this.spreadsheetIDResults = spreadsheetIDResults;
            this.XMLFlag = Boolean.parseBoolean(XMLFlag);
            this.XML_URL = XML_URL;

            if(this.googleSheetFlag) {
                Logger.Heading("Initialising Google Sheets");
                GoogleSheetUtil.getInstance();
                Logger.Log("Google Sheets Initialised");
            }

            Logger.Heading("Initialising WebDriver");
            WebDriverManager.chromedriver().setup();
            Logger.Log("WebDriver Initialised");
        } catch (Exception e) {
            Logger.Trace("Exception Occurred, message:" + e);
            Assert.fail("Exception Occurred, message:" + e);
        }
    }

    @BeforeMethod
    public void setUpTest() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL );
        //chromeOptions.setCapability( "goog:loggingPrefs", logPrefs );
        //chromeOptions.addArguments("--auto-open-devtools-for-tabs");
        if(browserMode.equalsIgnoreCase("headless")) {
            chromeOptions.addArguments("--headless");
        }

        WEBDRIVER_THREADLOCAL.set(new ChromeDriver(chromeOptions));
        WEBDRIVER_THREADLOCAL.get().manage().window().maximize();
    }

    @AfterMethod
    public void tearDownTest() {
        try {
            if(googleSheetFlag) {
                GoogleSheetUtil.getInstance().updateSheet(spreadsheetIDResults, "Sheet1!A1:B", Results);
            }
            //xlUtils.writeTable("reports/analytics.xlsx", "", table, false);
        } catch (IOException e) {
            Logger.Trace("Exception Occurred | message: " + e);
        } finally {
            WebDriver driver = WEBDRIVER_THREADLOCAL.get();
            if (driver != null) {
                driver.quit();
            }
        }
    }

    @DataProvider(name="PageBatchesProvider", parallel = true)
    public Object[][] PageBatchesProvider() {
        Object[][] batches = null;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        Logger.Heading("Creating Batches");
        try {
            if(googleSheetFlag)
            {
                List<List<String>> table = (List<List<String>>)(List<?>)GoogleSheetUtil.getInstance().readSheet(spreadsheetIDTestdata, "Locations!A2:ZZ");

                batches = new Object[table.size()][2];
                for (int i = 0; i < table.size(); i++) {
                    String sheetName = table.get(i).get(1);

                    ArrayList<ProdElement> elms = new ArrayList<>();
                    if(table.get(i).get(2).equalsIgnoreCase("xpath")) {
                        List<List<String>> paramTable = (List<List<String>>)(List<?>)GoogleSheetUtil.getInstance().readSheet(spreadsheetIDTestdata, sheetName+"!A1:E");
                        String triggeredBy = paramTable.get(0).get(1);

                        ArrayList<Filter> paramsFilter = new ArrayList<>();
                        for(int row = 2; row < paramTable.size(); row++) {
                            paramsFilter.add(new Filter(paramTable.get(row).get(0),
                                    paramTable.get(row).get(1),
                                    paramTable.get(row).get(2),
                                    paramTable.get(row).get(3),
                                    paramTable.get(row).get(4)));
                        }

                        driver.get(table.get(i).get(0));
                        elms.addAll(ProdElement.Get(driver, triggeredBy, paramsFilter));
                    }

                    batches[i] = new Object[]{String.format("Testcase%d",i+1), table.get(i).get(0), elms};
                }
            } else if(XMLFlag) {
                Logger.Heading("Initialising using XML");
                ArrayList<String> urls = new ArrayList<>(Sitemap.GetUsingXML(driver, XML_URL));
                Logger.Log("Done Initialising");
                for (int i = 0; i < urls.size(); i++) {
                    driver.get(urls.get(i));
                    Logger.Log("make: " + urls.get(i));
                    ArrayList<ProdElement> elms = new ArrayList<>();
                    ArrayList<Filter> paramsFilter = new ArrayList<>();
                    paramsFilter.add(new Filter("t", "string", "event", "", "exact"));
                    paramsFilter.add(new Filter("promo1id", "xpath", "/a", "", "partial"));
                    elms.addAll(ProdElement.Get(driver, "//article[@class='addrepet']", paramsFilter));
                    elms.addAll(ProdElement.Get(driver, "//div[@class='offercard']", paramsFilter));

                    batches[i] = new Object[]{urls.get(i), elms};
                }
            }
            else {
                /*ArrayList<Sitemap.SitemapItem> sitemapItems = Sitemap.Get(driver);

                ArrayList<Sitemap.SitemapItem> flattened = new ArrayList<>();
                for (Sitemap.SitemapItem item : sitemapItems) {
                    flattened.addAll(Sitemap.SitemapItem.Flatten(item));
                }

                batches = new Object[flattened.size()][2];
                for (int i = 0; i < flattened.size(); i++) {
                    driver.get(flattened.get(i).getLink());

                    ArrayList<ProdElement> elms = new ArrayList<>();
                    elms.addAll(ProdElement.Get(driver, "//article[@class='addrepet']", "//div[@class='amazonContent']", "/a"));
                    elms.addAll(ProdElement.Get(driver, "//div[@class='offercard']", "//div[@class='amazonContent']", "/a"));

                    batches[i] = new Object[]{flattened.get(i).getLink(), elms};
                }*/
            }
        } catch (Exception e) {
            Logger.Trace("Exception Occurred | message:" + e);
            Assert.fail("Exception Occurred | message:" + e);
        } finally {
            driver.quit();
        }
        Logger.Log("Batches Created");
        return batches;
    }

    @Test(dataProvider="PageBatchesProvider")
    public void VerifyGA(String TestID, String PageURL, ArrayList<ProdElement> elements) {
        Logger.Heading("TestID: " + TestID);
        SoftAssert sAssert = new SoftAssert();
        WebDriver driver = WEBDRIVER_THREADLOCAL.get();
        Map<String, Map<String, String>> collectRequests = new HashMap<>();
        ArrayList<ProdElement> NotTracked = new ArrayList<>();


        JavascriptExecutor executor = (JavascriptExecutor) driver;
        DevTools devTool = ((HasDevTools) driver).getDevTools();
        devTool.createSession();
        devTool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTool.addListener(Network.requestWillBeSent(), requestSent -> {
            if(requestSent.getRequest().getUrl().toLowerCase().contains("/collect?")) {
                String url = requestSent.getRequest().getUrl();
                collectRequests.put(url, NetworkUtils.parseGETQuery(url));
            }
        });

        try {
            driver.get(PageURL);

            for (ProdElement element : elements) {
                WebElement wElm = driver.findElement(By.xpath(element.getLocator()));
                executor.executeScript("arguments[0].scrollIntoView(true);", wElm);
                new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(wElm));

                Thread.sleep(RequestWaitTime);
            }

            for (ProdElement elem : elements) {
                boolean WasCollected = false;
                ArrayList<Filter> filters = elem.getFilters();
                for (Map<String, String> request : collectRequests.values()) {
                    int matches = 0;
                    for (Filter filter : filters) {
                        if (request.get(filter.getFieldname()) == null) continue;

                        String value = request.get(filter.getFieldname());
                        String searchFor = "";
                        String fValue = filter.getValue();
                        if (filter.getType().equalsIgnoreCase("xpath")) {
                            searchFor = driver.findElement(By.xpath(elem.getLocator() + fValue)).getAttribute(filter.getXpathAttribute());
                        } else {
                            searchFor = fValue;
                        }

                        if (filter.getMatchMethod().equals("exact")) {
                            if (searchFor.equals(value)) matches++;
                        } else if (filter.getMatchMethod().equals("partial")) {
                            if (searchFor.contains(value) || value.contains(searchFor)) matches++;
                        }
                    }

                    if (matches == filters.size()) {
                        WasCollected = true;
                        break;
                    }
                }
                if (!WasCollected) NotTracked.add(elem);
                sAssert.assertTrue(WasCollected, String.format("Request not sent | locator: %s | filters: %s", elem.getLocator(), elem.getFilters().toString()));
            }
        } catch (Exception e)
        {
            Logger.Trace("Exception Occurred | message:" + e);
            Assert.fail("Exception Occurred | message:" + e);
        } finally {
            GenerateReports(TestID, PageURL, NotTracked);
            sAssert.assertAll();
        }
    }

    private void GenerateReports(String TestID, String PageURL, ArrayList<ProdElement> NotTracked) {
        try {
            List<List<Object>> table = new ArrayList<>();
            String TestResult = "FAIL";
            if(NotTracked.isEmpty()) TestResult = "PASS";
            table.add(new ArrayList<>(Arrays.asList("TestID: " +  TestID, "Result: " + TestResult)));
            table.add(new ArrayList<>(Arrays.asList("Not Tracked Items List", "Page: " + PageURL)));
            table.add(new ArrayList<>(Arrays.asList("Product Locator", "Filters")));

            Logger.Log("Not Tracked Items List");
            for(ProdElement elm : NotTracked)
            {
                table.add(new ArrayList<>(Arrays.asList(elm.getLocator(), elm.getFilters().toString())));
                Logger.Log(elm.getLocator() + "\t" + elm.getFilters());
            } if(NotTracked.isEmpty()) {
                table.add(new ArrayList<>(List.of("None")));
                Logger.Log("None");
            }

            synchronized (Results) {
                Results.addAll(table);
                Results.add(new ArrayList<>());
            }
        } catch (Exception e) {
            Logger.Trace("Exception occurred | message: " + e);
        }
    }
}
