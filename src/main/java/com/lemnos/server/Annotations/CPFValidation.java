package com.lemnos.server.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@jakarta.validation.constraints.Pattern.List({@Pattern(
        regexp = "([0-9]{3}?[0-9]{3}?[0-9]{3}[0-9]{2})|([0-9]{11})"
), @Pattern(
        regexp = "^(?:(?!000\\?000\\?000?00).)*$"
), @Pattern(
        regexp = "^(?:(?!111\\?111\\?111?11).)*$"
), @Pattern(
        regexp = "^(?:(?!222\\?222\\?222?22).)*$"
), @Pattern(
        regexp = "^(?:(?!333\\?333\\?333?33).)*$"
), @Pattern(
        regexp = "^(?:(?!444\\?444\\?444?44).)*$"
), @Pattern(
        regexp = "^(?:(?!555\\?555\\?555?55).)*$"
), @Pattern(
        regexp = "^(?:(?!666\\?666\\?666?66).)*$"
), @Pattern(
        regexp = "^(?:(?!777\\?777\\?777?77).)*$"
), @Pattern(
        regexp = "^(?:(?!888\\?888\\?888?88).)*$"
), @Pattern(
        regexp = "^(?:(?!999\\?999\\?999?99).)*$"
)})
@ReportAsSingleViolation
@Documented
@Constraint(
        validatedBy = {}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFValidation {
    String message() default "{org.hibernate.validator.constraints.br.CPF.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        org.hibernate.validator.constraints.br.CPF[] value();
    }
}