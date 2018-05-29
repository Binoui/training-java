package com.excilys.formation.cdb.validators;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Computer;

public class ComputerValidator {
    
    private ComputerValidator() {}

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ComputerValidator.class);

    public static void validateComputer(Computer c) throws IncorrectValidationException {
        validateName(c.getName());
        validateDates(c.getIntroduced(), c.getDiscontinued());
    }

    private static void validateDates(Optional<LocalDate> optionalIntroduced, Optional<LocalDate> optionalDiscontinued)
            throws InvalidDatesException {
        LocalDate introduced = optionalIntroduced.orElse(null);
        LocalDate discontinued = optionalDiscontinued.orElse(null);

        if ((introduced != null) && (discontinued != null) && !introduced.isBefore(discontinued)) {
            throw new InvalidDatesException("Discontinued date cannot be before introducted date.");
        }
    }

    private static void validateName(String name) throws NullNameException {
        if ((name == null) || name.isEmpty()) {
            throw new NullNameException("Computer name cannot be null.");
        }
    }
}
