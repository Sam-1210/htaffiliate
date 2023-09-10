package org.shopnow.annotations;

import org.shopnow.enums.Environment;
import org.shopnow.enums.ExecutionType;
import org.shopnow.enums.Platforms;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestFilter {
    Platforms[] platform() default {};
    Environment[] environment() default {};
    ExecutionType executionType() default ExecutionType.SANITY;
}