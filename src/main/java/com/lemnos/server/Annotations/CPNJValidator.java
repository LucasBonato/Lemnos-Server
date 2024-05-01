package com.lemnos.server.Annotations;

import com.lemnos.server.Exceptions.Global.CnpjNotValidException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPNJValidator implements ConstraintValidator<CNPJValidation, Long> {
    @Override
    public void initialize(CNPJValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long cnpj, ConstraintValidatorContext context) {
        if(cnpj == null) return false;

        String message = isValidCNPJ(cnpj.toString());
        if(!message.equals("CNPJ válido")){
            context.disableDefaultConstraintViolation();
            throw new CnpjNotValidException("CNPJ inválido: " + message);
        }
        return true;
    }

    private String isValidCNPJ(String cnpj) {
        if(!cnpj.matches("[0-9]{14}")) return "Formato errado!";

        boolean allDigitsEqual = true;
        for (int i = 1; i < cnpj.length(); i++) {
            if (cnpj.charAt(i) != cnpj.charAt(0)) {
                allDigitsEqual = false;
                break;
            }
        }
        if(allDigitsEqual) return "Todos os números iguais";

        return "CNPJ válido";
    }
}
