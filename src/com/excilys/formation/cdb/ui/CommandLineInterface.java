package com.excilys.formation.cdb.ui;

import java.util.Scanner;

public class CommandLineInterface {
	public String getMainMenu() {
		StringBuilder menuBuilder = new StringBuilder();
		
		menuBuilder.append("******** Computer Database ********\n");
		menuBuilder.append("Possible features : \n");
		menuBuilder.append("    (1) List Companies\n");
		menuBuilder.append("    (2) List Computers\n");
		menuBuilder.append("    (3) Create Computer\n");
		menuBuilder.append("    (4) Update Computer\n");
		menuBuilder.append("    (5) Delete Computer\n");
		menuBuilder.append("    (6) Delete Computer\n");
		menuBuilder.append("Your choice : ");
		
		return menuBuilder.toString();
	}
	
	public String getCompanyList() {
		return "";
	}
	
	public String getComputerList() {
		return "";
	}
	
	
	public static void main(String[] arg) {
		CommandLineInterface cli = new CommandLineInterface();
		Scanner scanner = new Scanner(System.in);
		int menuChoice = 0;

		while (true) {
			System.out.println(cli.getMainMenu());
			
			menuChoice = scanner.nextInt();
			switch (menuChoice) {
				case 1 :
					System.out.println(cli.getCompanyList());
					break;
				case 2 :
					System.out.println(cli.getComputerList());
					break;
				case 3 :
					break;
				case 4 :
					
					break;
				case 5 :
					
					break;
				case 6 : 
					System.out.println("Closing Computer Database...");
					System.exit(0);					
					break;
				default : 
					System.out.println("Incorrect Choice");
			}
		}
	}
}
