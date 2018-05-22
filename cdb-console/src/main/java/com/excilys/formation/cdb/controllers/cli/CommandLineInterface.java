package com.excilys.formation.cdb.controllers.cli;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import com.excilys.formation.cdb.config.ConsoleConfig;
import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.CompanyDTOMapper;
import com.excilys.formation.cdb.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.validators.InvalidDatesException;

@Controller
public class CommandLineInterface {

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(CommandLineInterface.class);

    private static final String REST_URL = "http://localhost:8080/cdb-webservice/";

    public static void main(String[] arg) {
        @SuppressWarnings("resource")
        ApplicationContext context = new AnnotationConfigApplicationContext(ConsoleConfig.class);

        CommandLineInterface cli = context.getBean(CommandLineInterface.class);
        System.out.println("******** Computer Database ********\n");
        while (cli.menuLoop()) {
        }
    }

    private Client client;
    private Scanner scanner;

    public CommandLineInterface() {
        scanner = new Scanner(System.in);
        this.client = ClientBuilder.newClient();
    }

    private void closeScanner() {
        scanner.close();
    }

    private void createComputer() {
        Computer c = new Computer();
        readComputer(c);
        client.target(REST_URL).path("computer/").request()
                .post(Entity.entity(ComputerDTOMapper.createComputerDTO(c), MediaType.APPLICATION_JSON));
        System.out.println("Created new computer");
    }

    private void deleteCompany() {
        Long id = readNotNullId();
        client.target(REST_URL).path("company/" + id).request().delete();
        System.out.println("Company deleted !");
    }

    private void deleteComputer() {
        Long id = readNotNullId();
        client.target(REST_URL).path("computer/" + id).request().delete();
        System.out.println("Computer deleted !");
    }

    private void getCompanyList() {
        System.out.println("******** Companies List ********");
        int pageSize = 10;
        int pageCount = client.target(REST_URL).path("companies/size/" + pageSize + "/count")
                .request(MediaType.APPLICATION_JSON).get(Integer.class);
        Logger.debug("pageCount : " + pageCount);
        GenericType<List<CompanyDTO>> genericType = new GenericType<List<CompanyDTO>>() {
        };
        WebTarget companiesPath = client.target(REST_URL).path("companies");
        readPages(companiesPath, genericType, (c -> CompanyDTOMapper.createCompanyFromDto((CompanyDTO) c)), pageCount,
                pageSize);
    }

    private void getComputerList() {
        System.out.println("******** Computer List ********");

        int pageSize = 10;
        int pageCount = client.target(REST_URL).path("computers/size/" + pageSize + "/count")
                .request(MediaType.APPLICATION_JSON).get(Integer.class);
        GenericType<List<ComputerDTO>> genericType = new GenericType<List<ComputerDTO>>() {
        };
        WebTarget computersPath = client.target(REST_URL).path("computers");
        try {
            readPages(computersPath, genericType, (c -> {
                try {
                    return ComputerDTOMapper.createComputerFromDto((ComputerDTO) c);
                } catch (InvalidDatesException e) {

                }
                return null;
            }), pageCount, pageSize);
        } catch (Exception e) {

        }
    }

    private void getDetailsComputer() {
        Long id = readNotNullId();
        ComputerDTO c = null;

        c = client.target(REST_URL).path("computer").path("" + id).request(MediaType.APPLICATION_JSON)
                .get(ComputerDTO.class);
        try {
            System.out.println(ComputerDTOMapper.createComputerFromDto(c));
        } catch (InvalidDatesException e) {
            Logger.error("Wrong dates {}", e);
        }
    }

    private void getMainMenu() {
        StringBuilder menuBuilder = new StringBuilder();

        menuBuilder.append("\n******** Main Menu ********\n");
        menuBuilder.append("Possible features : \n");
        Stream.of(MenuChoice.values()).forEach(choice -> menuBuilder.append(choice.getValue()));
        System.out.print(menuBuilder.toString());
    }

    public boolean menuLoop() {
        getMainMenu();

        int menuChoice = readChoice();

        switch (MenuChoice.values()[menuChoice]) {
        case LISTCOMPANIES:
            getCompanyList();
            break;
        case LISTCOMPUTERS:
            getComputerList();
            break;
        case GETCOMPUTERDETAILS:
            getDetailsComputer();
            break;
        case CREATECOMPUTER:
            createComputer();
            break;
        case UPDATECOMPUTER:
            updateComputer();
            break;
        case DELETECOMPUTER:
            deleteComputer();
            break;
        case DELETECOMPANY:
            deleteCompany();
            break;
        case QUIT:
            System.out.println("Closing Computer Database...");
            closeScanner();
            System.out.println("Goodbye !");
            return false;
        default:
            System.out.println("Incorrect Choice");
        }

        return true;
    }

    private int readChoice() {
        System.out.print("Enter choice : ");

        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Please enter a valid choice (between 1 and " + MenuChoice.values().length + ") : ");
        }

        int choice = scanner.nextInt() - 1;
        scanner.nextLine();
        return choice;
    }

    private void readComputer(Computer c) {
        System.out.print("Enter new computer's name : ");
        c.setName(scanner.nextLine().trim());

        System.out.print("Enter new computer's introduction date (yyyy-mm-dd) : ");
        c.setIntroduced(readDate());

        System.out.print("Enter new computer's discontinuation date (yyyy-mm-dd) : ");
        c.setDiscontinued(readDate());

        Company company = new CompanyBuilder().withId(readId()).build();
        c.setCompany(company);
    }

    private LocalDate readDate() {
        LocalDate readDate = null;
        String readString;
        boolean acceptable = false;
        while (!acceptable) {
            readString = scanner.nextLine().trim();
            if (readString.isEmpty()) {
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

    private Long readId() {
        Long readId = null;
        String readString;
        boolean acceptable = false;
        while (!acceptable) {
            System.out.print("Enter new computer's company ID : ");
            readString = scanner.nextLine().trim();
            if (readString.isEmpty()) {
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

    private Long readNotNullId() {
        System.out.print("Enter wanted ID : ");

        while (!scanner.hasNextLong()) {
            scanner.next();
            System.out.println("Please enter a valid ID : ");
        }

        Long id = scanner.nextLong();
        scanner.nextLine();
        return id;
    }

    private <T> void readPages(WebTarget restPath, GenericType<List<T>> genericType, DTOMapper dtoMapper, int pageCount,
            int pageSize) {
        String choice = "f";

        int page = 0;
        while (!choice.equals("q")) {
            switch (choice) {
            case "n":
                if (page < (pageCount - 1)) {
                    page++;
                }
                break;
            case "p":
                if (page >= 0) {
                    page--;
                }
                break;
            case "f":
                page = 0;
                break;
            case "l":
                page = pageCount - 1;
                break;
            case "q":
                System.out.println("Closing.");
                return;
            default:
                System.out.println("Wrong choice.");
            }

            Response responseEntity = restPath.path("/page/" + page).path("/size/" + pageSize)
                    .request(MediaType.APPLICATION_JSON).get(Response.class);
            responseEntity.readEntity(genericType).stream().map(c -> dtoMapper.FromDTO(c)).forEach(System.out::println);

            System.out.println("Reading Pages. Possible choices : [n]ext, [p]revious, [f]irst, [l]ast, [q]uit");
            choice = scanner.nextLine();
        }
    }

    private void updateComputer() {
        Computer c = new Computer();
        c.setId(readNotNullId());
        readComputer(c);

        client.target(REST_URL).path("computer").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(ComputerDTOMapper.createComputerDTO(c), MediaType.APPLICATION_JSON));
    }
}

interface DTOMapper {
    Object FromDTO(Object a);
}
