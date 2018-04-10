package com.excilys.formation.cdb.validators;

import java.time.LocalDate;

import com.excilys.formation.cdb.model.Computer;

public class ComputerValidator {

    public static void validateComputer(Computer c) throws IncorrectValidationException {
        validateName(c.getName());
        validateDates(c.getIntroduced(), c.getDiscontinued());
    }

    private static void validateDates(LocalDate introduced, LocalDate discontinued) throws InvalidDatesException {
        if ((discontinued != null) && (introduced != null) && !introduced.isBefore(discontinued)) {
            throw new InvalidDatesException("Discontinued date cannot be before introducted date.");
        }
    }

    private static void validateName(String name) throws NullNameException {
        if ((name == null) || name.isEmpty()) {
            throw new NullNameException("Computer name cannot be null.");
        }
    }
}
