package com.excilys.formation.cdb.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.stream.Stream;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.validators.IncorrectValidationException;
import com.excilys.formation.cdb.validators.UnknownComputerIdException;

public class CommandLineInterface {

	private final static int PAGE_SIZE = 20;

	private ComputerService computerService = ComputerService.INSTANCE;
	private CompanyService companyService = CompanyService.INSTANCE;

	private Scanner scanner;

	public CommandLineInterface() {
		scanner = new Scanner(System.in);
	}

	public void menuLoop() {
		String menuChoice;

		getMainMenu();

		menuChoice = readChoice();

		switch (menuChoice) {
		case "1":
			getCompanyList();
			break;
		case "2":
			getComputerList();
			break;
		case "3":
			getDetailsComputer();
			break;
		case "4":
			createComputer();
			break;
		case "5":
			updateComputer();
			break;
		case "6":
			deleteComputer();
			break;
		case "7":
			System.out.println("Closing Computer Database...");
			closeScanner();
			System.out.println("Goodbye !");
			System.exit(0);
		default:
			System.out.println("Incorrect Choice");
		}
	}

	private void getMainMenu() {
		StringBuilder menuBuilder = new StringBuilder();

		menuBuilder.append("\n******** Main Menu ********\n");
		menuBuilder.append("Possible features : \n");
		Stream.of(MenuChoice.values()).forEach(choice -> menuBuilder.append(choice.getValue()));
		menuBuilder.append("Your choice : ");

		System.out.print(menuBuilder.toString());
	}

	private void getCompanyList() {
		System.out.println("******** Companies List ********");

		for (int i = 0; i < companyService.getListCompaniesPageCount(PAGE_SIZE); i++) {
			companyService.getListCompanies(i, PAGE_SIZE).forEach(System.out::println);
			scanner.nextLine();
		}
	}

	private void getComputerList() {
		System.out.println("******** Computer List ********");
		for (int i = 0; i < computerService.getListComputersPageCount(PAGE_SIZE); i++) {
			computerService.getListComputers(i, PAGE_SIZE).forEach(System.out::println);
			scanner.nextLine();
		}
	}
	
	private void getDetailsComputer() {
		Long id = readNotNullId();
		Computer c = computerService.getComputer(id);
		if (c != null) {
			System.out.println(c);
		} else {
			System.out.println("No computer found with id " + id);
		}
	}

	private void createComputer() {
		Computer c = new Computer();
		readComputer(c);

		try {
			computerService.createComputer(c);
		} catch (IncorrectValidationException e) {
			e.printStackTrace();
		}
	}

	private void updateComputer() {
		Computer c = new Computer();
		c.setId(readNotNullId());
		readComputer(c);
		
		try {
			computerService.updateComputer(c);
		} catch (IncorrectValidationException e) {
			System.out.println(e.getMessage());
		}
	}

	private void deleteComputer() {
		Long id = readNotNullId();
		try {
			computerService.deleteComputer(id);
		} catch (UnknownComputerIdException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void readComputer(Computer c) {
		System.out.print("Enter new computer's name : ");
		c.setName(scanner.nextLine().trim());
		
		System.out.print("Enter new computer's introduction date (yyyy-mm-dd) : ");
		c.setIntroduced(readDate());

		System.out.print("Enter new computer's discontinuation date (yyyy-mm-dd) : ");
		c.setDiscontinued(readDate());

		Company company = new Company();
		company.setId(readId());
		c.setCompany(company);
	}
	
	private Long readNotNullId() {
		System.out.print("Enter ID of wanted computer : ");

		while (! scanner.hasNextLong()) {
			scanner.next();
			System.out.println("Please enter a valid ID : ");
		}
		
		Long id = scanner.nextLong();
		scanner.nextLine();
		return id;
	}
	
	private Long readId() {
		Long readId = null;
		String readString;
		boolean acceptable = false;
		while (! acceptable) {
			System.out.print("Enter new computer's company ID : ");
			readString = scanner.nextLine().trim();
			if (readString.isEmpty()) {
				readId = null;
				acceptable = true;
			} else {
				try {
					readId = Long.valueOf(readString);
					acceptable = true;
				} catch (NumberFormatException e) {
					System.out.println("Please enter a valid ID : "); 
				}
			}
		}
		
		return readId;
	}

	private LocalDate readDate() {
		LocalDate readDate = null;
		String readString;
		boolean acceptable = false;
		while (! acceptable) {
			readString = scanner.nextLine().trim();
			if (readString.isEmpty()) {
				readDate = null;
				acceptable = true;
			} else {
				try {
					readDate = LocalDate.parse(readString);
					acceptable = true;
				} catch (DateTimeParseException e) {
					System.out.println("Incorrect date format (please use yyyy-mm-dd)");
				}
			}
		}
		
		return readDate;
	}

	private String readChoice() {
		return scanner.nextLine();
	}
	
	private void closeScanner() {
		scanner.close();
	}

	public static void main(String[] arg) {
		System.out.println("******** Computer Database ********\n");
		CommandLineInterface cli = new CommandLineInterface();
		while (true) {
			cli.menuLoop();
		}
	}
}
