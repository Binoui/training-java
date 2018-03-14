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

		menuBuilder.append("******** Computer Database ********\n");
		menuBuilder.append("Possible features : \n");
		Stream.of(MenuChoice.values()).forEach(choice -> menuBuilder.append(choice.getValue()));
		menuBuilder.append("Your choice : ");

		System.out.print(menuBuilder.toString());
	}

	public void getCompanyList() {
		System.out.println("******** Companies List *******");

		for (int i = 0; i < companyService.getListCompaniesPageCount(PAGE_SIZE); i++) {
			companyService.getListCompanies(i, PAGE_SIZE).forEach(System.out::println);
			scanner.nextLine();
		}
	}

	public void getComputerList() {
		System.out.println("******** Computer List *******");
		for (int i = 0; i < computerService.getListComputersPageCount(PAGE_SIZE); i++) {
			computerService.getListComputers(i, PAGE_SIZE).forEach(System.out::println);
			scanner.nextLine();
		}
	}

	public void createComputer() {
		Computer c = readComputer();

		try {
			computerService.createComputer(c);
		} catch (IncorrectValidationException e) {
			e.printStackTrace();
		}
	}

	public void updateComputer() {
		Computer c = readComputer();
		
		try {
			computerService.updateComputer(c);
		} catch (IncorrectValidationException e) {
			e.printStackTrace();
		}
	}

	public void deleteComputer() {

	}
	
	public Computer readComputer() {
		Computer c = new Computer();
		System.out.print("Enter computer name : ");
		c.setName(scanner.nextLine());
		
		System.out.print("Enter the computer's introduction date (yyyy-mm-dd) : ");
		c.setIntroduced(readDate());

		System.out.print("Enter the computer's discontinuation date (yyyy-mm-dd) : ");
		c.setDiscontinued(readDate());

		System.out.print("Enter the computer's company ID : ");
		Company company = new Company();
		company.setId(readId());
		c.setCompany(company);
		return c;

	}
	
	public Long readId() {
		Long readId = null;
		String readString;
		boolean acceptable = false;
		while (! acceptable) {
			readString = scanner.nextLine().trim();
			if (readString.isEmpty()) {
				readId = null;
				acceptable = true;
			} else {
				try {
					readId = Long.valueOf(readString);
					acceptable = true;
				} catch (NumberFormatException e) {
					System.out.println("Incorrect company ID"); 
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

	public void closeScanner() {
		scanner.close();
	}

	public static void main(String[] arg) {
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
				cli.createComputer();
				break;
			case "4":
				cli.updateComputer();
				break;
			case "5":
				cli.deleteComputer();
				break;
			case "6":
				System.out.println("Closing Computer Database...");
				cli.closeScanner();
				System.exit(0);
			default:
				System.out.println("Incorrect Choice");
			}
		}

	}
}
