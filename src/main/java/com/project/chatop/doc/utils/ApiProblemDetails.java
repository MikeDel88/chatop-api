package com.project.chatop.doc.utils;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiProblemDetails {
        ApiProblemDetail[] value();
}
