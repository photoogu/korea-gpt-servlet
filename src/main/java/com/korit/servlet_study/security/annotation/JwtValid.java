package com.korit.servlet_study.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})       // Annotation 누구한테 적용할 지
@Retention(RetentionPolicy.RUNTIME) // Annotation 언제 적용할 지
public @interface JwtValid {

}
