package org.shopnow.listeners;

import org.shopnow.annotations.TestFilter;
import org.shopnow.enums.Environment;
import org.shopnow.enums.ExecutionType;
import org.shopnow.enums.Platforms;
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
            Platforms currentPlatform = ApplicationProperties.getInstance().getPlatform();
            Environment currentEnv = ApplicationProperties.getInstance().getEnvironment();
            ExecutionType executionType = ApplicationProperties.getInstance().getExecutionType();
            boolean shouldSkip = isMismatched(annotation.platform(), currentPlatform);
            shouldSkip |= isMismatched(annotation.environment(), currentEnv);
            shouldSkip |= isMismatched(annotation.executionType(), executionType);

            if(shouldSkip) throw new SkipException("Not a Test for This Application Configuration");
        }
        Logger.Heading("Test - " + MethodName);
    }

    private boolean isMismatched(Platforms[] requiredValues, Platforms currentValue) {
        boolean isMisMatch = requiredValues.length != 0;

        for (Platforms requiredValue : requiredValues) {
            if (requiredValue.name().equalsIgnoreCase(currentValue.name())) {
                isMisMatch = false;
                break;
            }
        }

        return isMisMatch ;
    }

    private boolean isMismatched(Environment[] requiredValues, Environment currentValue) {
        boolean isMisMatch = requiredValues.length != 0;
        for (Environment requiredValue : requiredValues) {
            if (requiredValue.name().equalsIgnoreCase(currentValue.name())) {
                isMisMatch = false;
                break;
            }
        }
        return isMisMatch;
    }

    private boolean isMismatched(ExecutionType requiredValue, ExecutionType currentValue) {
        boolean isMisMatch = true;
        if(currentValue.equals(ExecutionType.REGRESSION)) {
            isMisMatch = false; // is every case in regression env
        } else if(requiredValue.equals(ExecutionType.REGRESSION)) {
            isMisMatch = true; // is a regression case in sanity env
        } else {
            isMisMatch = false; // is a sanity case in sanity env
        }

        return isMisMatch;
    }
}
