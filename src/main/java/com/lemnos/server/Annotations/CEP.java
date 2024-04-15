package com.lemnos.server.Annotations;

import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//"([0-9]{5}-?[0-9]{4})"
@Pattern(regexp = "([0-9]{5}?[0-9]{4})")
@ReportAsSingleViolation
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CEP {
    String message() default "{com.lemnos.server.Annotations.CEP.message}";
}
