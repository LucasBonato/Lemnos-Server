package com.lemnos.server.Annotations;

import com.lemnos.server.Exceptions.Global.CpfNotValidException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CEPValidator implements ConstraintValidator<CEP, String> {
    @Override
    public void initialize(CEP constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        if(cep == null) return false;

        String message = isValidCEP(cep);
        if(!message.equals("CEP válido")){
            context.disableDefaultConstraintViolation();
            throw new CpfNotValidException("CEP inválido: " + message);
        }
        return true;
    }

    private String isValidCEP(String cep) {
        if (!cep.matches("[0-9]{8}")) return "Formato errado!";

        boolean allDigitsEqual = true;
        for (int i = 1; i < cep.length(); i++) {
            if (cep.charAt(i) != cep.charAt(0)) {
                allDigitsEqual = false;
                break;
            }
        }
        if(allDigitsEqual)return "Todos os números iguais";

        return "CEP válido";
    }
}
