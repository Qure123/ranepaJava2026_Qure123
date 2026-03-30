package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class HrmApplication {
    private static final String MENU_TITLE = "=== HRM System Menu ===";
    private static final String PROMPT_CHOICE = "Choose an option: ";
    private static final String PROMPT_NAME = "Enter name: ";
    private static final String PROMPT_POSITION = "Enter position: ";
    private static final String PROMPT_SALARY = "Enter salary: ";
    private static final String PROMPT_DATE = "Enter hire date (YYYY-MM-DD): ";
    private static final String PROMPT_ID = "Enter employee ID: ";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    private final Scanner scanner;
    private final EmployeeRepository repository;
    private final EmployeeService service;
    public HrmApplication(EmployeeRepository repository, EmployeeService service) {
        this.scanner = new Scanner(System.in);  // Scanner создаётся здесь
        this.repository = repository;
        this.service = service;
    }
    //вынести сканнер и репозиторий и сервис - убрать из методов
    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepositoryImpl();
        EmployeeService service = new EmployeeService(repository);
        HrmApplication app = new HrmApplication(repository, service);
        app.run();
    }
    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readChoice();
            switch (choice) {
                case 1 -> showAllEmployees();
                case 2 -> addEmployee();
                case 3 -> deleteEmployee();
                case 4 -> findEmployee();
                case 5 -> showStatistics();
                case 6 -> showSortedEmployees(service.sortByHireDate());
                case 7 -> filterEmployeesByPosition();
                case 8 -> exportToCsv();
                case 9 -> { running = false; System.out.println("Goodbye!"); }
                default -> System.out.println("Invalid option, try again");
            }
        }
        scanner.close();
    }
    private void printMenu () {
        System.out.println("\n" + MENU_TITLE);
        System.out.println("1. Show all employees");
        System.out.println("2. Add employee");
        System.out.println("3. Delete employee by ID");
        System.out.println("4. Find employee by ID");
        System.out.println("5. Show statistics");
        System.out.println("6. Sort by hire date (old→new)");
        System.out.println("7. Filter by position");
        System.out.println("8. Export to CSV");
        System.out.println("9. Exit");
        System.out.print(PROMPT_CHOICE);
    }
    private int readChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
            return -1;  // невалидный ввод
        }
    }
    private void showAllEmployees() {
        System.out.println("\nAll employees:");
        boolean found = false;
        for (Employee emp : repository.findAll()) {
            System.out.println(emp);
            found = true;
        }

        if (!found) {
            System.out.println("No employees found");
        }
    }
    // добавление и удаление перетащить на уровне сервиса
    private void addEmployee() {
        try {
            System.out.print(PROMPT_NAME);
            String name = scanner.nextLine();
            System.out.print(PROMPT_POSITION);
            String position = scanner.nextLine();
            System.out.print(PROMPT_SALARY);
            double salary = Double.parseDouble(scanner.nextLine().trim());
            System.out.print(PROMPT_DATE);
            LocalDate hireDate = LocalDate.parse(scanner.nextLine().trim(), DATE_FORMAT);
            Employee emp = new Employee(name, position, salary, hireDate);
            String result = service.addEmployee(emp);
            System.out.println(result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary format. Please enter a number.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private void deleteEmployee() {
        try {
            System.out.print(PROMPT_ID);
            Long id = Long.parseLong(scanner.nextLine().trim());
            String result = service.removeEmployee(id);
            System.out.println(result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }
    private void findEmployee() {
        try {
            System.out.print(PROMPT_ID);
            Long id = Long.parseLong(scanner.nextLine().trim());
            if (service.findEmployeeById(id).isPresent()) {
                System.out.println("Found: " + service.findEmployeeById(id).get());
            } else {
                System.out.println("Employee not found");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }
    private void showStatistics() {
        System.out.println("\nCompany Statistics");
        System.out.println("Total employees: " + service.getEmployeeCount());
        BigDecimal avg = service.calculateAverageSalary();
        System.out.println("Average salary: " + avg);
        if (service.findTopEarner().isPresent()) {
            Employee top = service.findTopEarner().get();
            System.out.println("Top earner: " + top.getName() + " (" + top.getSalary() + ")");
        } else {
            System.out.println("No employees to analyze");
        }
    }
    private void showSortedEmployees(Collection<Employee> employees) {
        System.out.println("\nEmployees sorted " + "by hire date (old→new)" + ":");
        if (employees.isEmpty()) {
            System.out.println("No employees found");
            return;
        }
        int index = 1;
        for (Employee emp : employees) {
            System.out.println(index++ + ". " + emp);
        }
    }
    private void filterEmployeesByPosition() {
        System.out.print("Enter position to filter (e.g., Developer): ");
        String position = scanner.nextLine().trim();
        List<Employee> filtered = service.filterByPosition(position);
        System.out.println("\nEmployees with position '" + position + "':");
        if (filtered.isEmpty()) {
            System.out.println("No employees found with this position");
            return;
        }
        int index = 1;
        for (Employee emp : filtered) {
            System.out.println(index++ + ". " + emp);
        }
        System.out.println("Total: " + filtered.size() + " employee(s)");
    }
    private void exportToCsv() {
        System.out.print("Enter file path (e.g., employees.csv): ");
        String filePath = scanner.nextLine().trim();
        if (filePath.isEmpty()) {
            filePath = "employees.csv";
        }
        String result = repository.exportToCsv(filePath);
        System.out.println(result);
    }
}