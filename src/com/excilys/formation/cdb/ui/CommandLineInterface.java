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
import com.excilys.formation.cdb.validators.InvalidDatesException;
import com.excilys.formation.cdb.validators.NullNameException;
import com.excilys.formation.cdb.validators.UnknownCompanyIdException;
import com.excilys.formation.cdb.validators.UnknownComputerIdException;

public class CommandLineInterface {

	private final static int PAGE_SIZE = 20;

	private ComputerService computerService = ComputerService.INSTANCE;
	private CompanyService companyService = CompanyService.INSTANCE;

	private Scanner scanner;

	public CommandLineInterface() {
		scanner = new Scanner(System.in);
	}

	public void getMainMenu() {
		StringBuilder menuBuilder = new StringBuilder();

		menuBuilder.append("\n******** Main Menu ********\n");
		menuBuilder.append("Possible features : \n");
		Stream.of(MenuChoice.values()).forEach(choice -> menuBuilder.append(choice.getValue()));
		menuBuilder.append("Your choice : ");

		System.out.print(menuBuilder.toString());
	}

	public void getCompanyList() {
		System.out.println("******** Companies List ********");

		for (int i = 0; i < companyService.getListCompaniesPageCount(PAGE_SIZE); i++) {
			companyService.getListCompanies(i, PAGE_SIZE).forEach(System.out::println);
			scanner.nextLine();
		}
	}

	public void getComputerList() {
		System.out.println("******** Computer List ********");
		for (int i = 0; i < computerService.getListComputersPageCount(PAGE_SIZE); i++) {
			computerService.getListComputers(i, PAGE_SIZE).forEach(System.out::println);
			scanner.nextLine();
		}
	}
	
	public void getDetailsComputer() {
		Long id = readNotNullId();
		Computer c = computerService.getComputer(id);
		if (c != null) {
			System.out.println(c);
		} else {
			System.out.println("No computer found with id " + id);
		}
	}

	public void createComputer() {
		Computer c = new Computer();
		readComputer(c);

		try {
			computerService.createComputer(c);
		} catch (IncorrectValidationException e) {
			e.printStackTrace();
		}
	}

	public void updateComputer() {
		Computer c = new Computer();
		c.setId(readNotNullId());
		readComputer(c);
		
		try {
			computerService.updateComputer(c);
		} catch (IncorrectValidationException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteComputer() {
		Long id = readNotNullId();
		try {
			computerService.deleteComputer(id);
		} catch (UnknownComputerIdException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void readComputer(Computer c) {
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
	
	public Long readNotNullId() {
		System.out.print("Enter ID of wanted computer : ");

		while (! scanner.hasNextLong()) {
			scanner.next();
			System.out.println("Please enter a valid ID : ");
		}
		
		Long id = scanner.nextLong();
		scanner.nextLine();
		return id;
	}
	
	public Long readId() {
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

	public LocalDate readDate() {
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

	public String readChoice() {
		return scanner.nextLine();
	}
	
	public void clearScanner() {
		if (scanner.hasNextLine()) scanner.nextLine();
	}

	public void closeScanner() {
		scanner.close();
	}

	public static void main(String[] arg) {
		System.out.println("******** Computer Database ********\n");

		CommandLineInterface cli = new CommandLineInterface();
		String menuChoice = "";

		while (true) {
			cli.getMainMenu();

			menuChoice = cli.readChoice();

			switch (menuChoice) {
			case "1":
				cli.getCompanyList();
				break;
			case "2":
				cli.getComputerList();
				break;
			case "3":
				cli.getDetailsComputer();
				break;
			case "4":
				cli.createComputer();
				break;
			case "5":
				cli.updateComputer();
				break;
			case "6":
				cli.deleteComputer();
				break;
			case "7":
				System.out.println("Closing Computer Database...");
				cli.closeScanner();
				System.out.println("Goodbye !");
				System.exit(0);
			default:
				System.out.println("Incorrect Choice");
			}
		}

	}
}
