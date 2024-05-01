package com.lemnos.server.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.*;

//"([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}/?[0-9]{4}-?[0-9]{2})"
//"([0-9]{2}?[0-9]{3}?[0-9]{3}?[0-9]{4}?[0-9]{2})"
@Documented
@ReportAsSingleViolation
@Constraint(validatedBy = CPNJValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CNPJValidation {
    String message() default "CNPJ inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}