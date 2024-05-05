package com.lemnos.server.annotations;

import com.lemnos.server.annotations.validators.CPNJValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@ReportAsSingleViolation
@Constraint(validatedBy = CPNJValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CNPJ {
    String message() default "CNPJ inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}