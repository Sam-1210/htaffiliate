package org.shopnow.utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.shopnow.enums.BrowserMode;
import org.shopnow.enums.SupportedBrowsers;
import org.shopnow.structures.ApplicationProperties;

public class DriverManager {
    @Getter
    private WebDriver driver;
    private static final ThreadLocal<DriverManager> INSTANCES = new InheritableThreadLocal<>();

    private DriverManager() {
        SupportedBrowsers browser = ApplicationProperties.getInstance().getBrowser();
        BrowserMode browserMode = ApplicationProperties.getInstance().getBrowserMode();
        Logger.Heading("Setting Up Driver");
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                switch (browserMode) {
                    case HEADLESS:
                        options.addArguments("--headless");
                        options.addArguments("--window-size=1366,768");
                        break;
                    case PRIVATE:
                        options.addArguments("--incognito");
                        break;
                }
                options.addArguments("--remote-allow-origins=*");
                options.setCapability("unhandledPromptBehavior", "accept");

                driver = new ChromeDriver(options);
                break;
        }
        Logger.Log("Driver Initialised");
    }


    public static DriverManager getInstance() {
        if(INSTANCES.get() == null) {
            INSTANCES.set(new DriverManager());
        }
        return INSTANCES.get();
    }

    public void Quit() {
        if(driver != null)
        {
            INSTANCES.remove();
            driver.quit();
        }
    }

    public static void QuitAll() {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if(INSTANCES.get() != null)
                INSTANCES.get().Quit();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            Logger.Log("Trying To Force Quit Driver");
            if (driver != null) {
                INSTANCES.remove();
                driver.quit();
            }
        } finally {
            super.finalize();
        }
    }
}
