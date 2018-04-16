package com.excilys.formation.cdb.validators;

public class InvalidDatesException extends IncorrectValidationException {

    public InvalidDatesException() {
        super();
    }

    public InvalidDatesException(String message) {
        super(message);
    }
}