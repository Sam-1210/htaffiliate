package org.shopnow.listeners;

import org.shopnow.annotations.TestFilter;
import org.shopnow.structures.ApplicationProperties;
import org.shopnow.utility.Logger;
import org.testng.ITestResult;
        import org.testng.SkipException;
        import org.testng.TestListenerAdapter;

import java.util.Arrays;

public class TestListener extends TestListenerAdapter {
    @Override
    public void onTestStart(ITestResult result) {
        TestFilter annotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestFilter.class);
        String MethodName = result.getMethod().getMethodName();
        if (annotation != null) {
            String currentPlatform = ApplicationProperties.getInstance().getPlatform().name();
            String currentEnv = ApplicationProperties.getInstance().getEnvironment().name();
            String executionType = ApplicationProperties.getInstance().getExecutionType().name();
            boolean shouldSkip = isMismatched(annotation.platform(), currentPlatform);
            shouldSkip &= isMismatched(annotation.environment(), currentEnv);
            shouldSkip &= isMismatched(annotation.executionType(), executionType);

            if(shouldSkip) throw new SkipException("Not a Test for This Application Configuration");
        }
        Logger.Heading("Test - " + MethodName);
    }

    private boolean isMismatched(String[] requiredValues, String currentValue) {
        boolean isMisMatch = true;
        for (String requiredValue : requiredValues) {
            if (requiredValue.equalsIgnoreCase(currentValue)) {
                isMisMatch = false;
                break;
            }
        }
        return isMisMatch;
    }
}
