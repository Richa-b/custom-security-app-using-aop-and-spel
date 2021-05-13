package com.example.demo.conf;

import com.example.demo.pojo.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProjectSecured {

    Role[] roles() default {};

    String projectIdField() default "";

}