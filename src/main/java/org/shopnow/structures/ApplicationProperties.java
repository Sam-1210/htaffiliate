package org.shopnow.structures;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shopnow.enums.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationProperties {
    private Environment environment;
    private ExecutionType executionType;
    private Platforms platform;
    private BrowserMode browserMode;
    private SupportedBrowsers browser;
    private boolean isBrowserStack;
    private boolean isAppium;
    private String capabilitiesJsonFileName;
    private boolean isGoogleSheet;
    private String homepageURL;
    private String currentRunningTest;

    private static final ThreadLocal<ApplicationProperties> instance = new InheritableThreadLocal<>();
    public static ApplicationProperties getInstance() {
        if (instance.get() == null)
            instance.set(new ApplicationProperties());
        return instance.get();
    }
}
