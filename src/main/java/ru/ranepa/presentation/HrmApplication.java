package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class HrmApplication {
    private static final String MENU_TITLE = "=== HRM System Menu ===";
    private static final String PROMPT_CHOICE = "Choose an option: ";
    private static final String PROMPT_NAME = "Enter name: ";
    private static final String PROMPT_POSITION = "Enter position: ";
    private static final String PROMPT_SALARY = "Enter salary: ";
    private static final String PROMPT_DATE = "Enter hire date (YYYY-MM-DD): ";
    private static final String PROMPT_ID = "Enter employee ID: ";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepositoryImpl();
        EmployeeService service = new EmployeeService(repository);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                int choice = readChoice(scanner);
                switch (choice) {
                    case 1 -> showAllEmployees(repository);
                    case 2 -> addEmployee(scanner, repository);
                    case 3 -> deleteEmployee(scanner, repository);
                    case 4 -> findEmployee(scanner, repository);
                    case 5 -> showStatistics(service);
                    case 6 -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }
                    default -> System.out.println("Invalid option, try again");
                }

            }