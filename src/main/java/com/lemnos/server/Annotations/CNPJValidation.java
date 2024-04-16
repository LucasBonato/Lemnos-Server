package com.lemnos.server.Annotations;

import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.*;

//"([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}/?[0-9]{4}-?[0-9]{2})"
@Pattern(regexp = "([0-9]{2}?[0-9]{3}?[0-9]{3}?[0-9]{4}?[0-9]{2})")
@ReportAsSingleViolation
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CNPJValidation {
    String message() default "{org.hibernate.validator.constraints.br.CNPJ.message}";
}