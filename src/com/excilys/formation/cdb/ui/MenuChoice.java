package com.excilys.formation.cdb.ui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum MenuChoice {

	LISTCOMPANIES("    (1) list companies\n"),
	LISTCOMPUTERS("    (2) list computers\n"),
	CREATECOMPUTER("    (3) Create Computer\n"),
	UPDATECOMPUTER("    (4) Update Computer\n"),
	DELETECOMPUTER("    (5) Delete Computer\n"),
	QUIT("    (6) Quit Computer Database\n");

	private final String value;
	
	MenuChoice(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
