package com.excilys.formation.cdb.ui;

public enum MenuChoice {

    LISTCOMPANIES("    (1) List companies\n"), LISTCOMPUTERS("    (2) List computers\n"), GETCOMPUTERDETAILS(
            "    (3) Get details computer\n"), CREATECOMPUTER("    (4) Create Computer\n"), UPDATECOMPUTER(
                    "    (5) Update Computer\n"), DELETECOMPUTER(
                            "    (6) Delete Computer\n"), QUIT("    (7) Quit Computer Database\n");

    private final String value;

    MenuChoice(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
