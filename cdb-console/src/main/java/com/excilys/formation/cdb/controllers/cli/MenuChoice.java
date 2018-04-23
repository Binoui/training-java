package com.excilys.formation.cdb.controllers.cli;

public enum MenuChoice {

    LISTCOMPANIES("\t(1) List companies\n"), LISTCOMPUTERS("\t(2) List computers\n"), GETCOMPUTERDETAILS(
            "\t(3) Get details computer\n"), CREATECOMPUTER("\t(4) Create Computer\n"), UPDATECOMPUTER(
                    "\t(5) Update Computer\n"), DELETECOMPUTER("\t(6) Delete Computer\n"), DELETECOMPANY(
                            "\t(7) Delete Company\n"), QUIT("\t(8) Quit Computer Database\n");

    private final String value;

    MenuChoice(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
