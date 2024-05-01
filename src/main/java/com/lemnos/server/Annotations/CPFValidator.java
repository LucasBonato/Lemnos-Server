package com.lemnos.server.Annotations;

import com.lemnos.server.Exceptions.Global.CpfNotValidException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPFValidation, Long> {
    @Override
    public void initialize(CPFValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long cpf, ConstraintValidatorContext context) {
        if(cpf == null) return false;

        String message = isValidCPF(cpf.toString());
        if(!message.equals("CPF válido")){
            context.disableDefaultConstraintViolation();
            throw new CpfNotValidException("CPF inválido: " + message);
        }
        return true;
    }

    private String isValidCPF(String cpf) {
        if (!cpf.matches("[0-9]{11}")) {
            return "Formato errado!";
        }

        boolean allDigitsEqual = true;
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                allDigitsEqual = false;
                break;
            }
        }
        if(allDigitsEqual){
            return "Todos os números iguais";
        }
        return "CPF válido";
    }
}
