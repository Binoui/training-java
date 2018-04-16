package com.excilys.formation.cdb.validators;

public class UnknownCompanyIdException extends IncorrectValidationException {

    public UnknownCompanyIdException() {
        super();
    }
    
    public UnknownCompanyIdException(String message) {
        super(message);
    }
}