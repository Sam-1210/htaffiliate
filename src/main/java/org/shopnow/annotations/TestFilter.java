package org.shopnow.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestFilter {
    String[] platform() default {};
    String[] environment() default {};
    String[] executionType() default {};
}