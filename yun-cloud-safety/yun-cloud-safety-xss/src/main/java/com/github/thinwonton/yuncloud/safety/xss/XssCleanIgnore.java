package com.github.thinwonton.yuncloud.safety.xss;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface XssCleanIgnore {
}
