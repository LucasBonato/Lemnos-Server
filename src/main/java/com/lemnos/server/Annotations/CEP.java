package com.lemnos.server.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CEPValidator.class)
@ReportAsSingleViolation
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CEP {
    String message() default "CEP inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
