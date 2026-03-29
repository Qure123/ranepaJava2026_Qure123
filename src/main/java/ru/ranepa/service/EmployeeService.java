package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class EmployeeService {
    private final EmployeeRepository repository;
    public EmployeeService(EmployeeRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }
    public BigDecimal calculateAverageSalary() { //расчет средней зп
        List<Employee> employees = new ArrayList<>(repository.findAll());
        if (employees.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (Employee emp : employees) {
            sum = sum.add(emp.getSalary());
        }
        return sum.divide(
                BigDecimal.valueOf(employees.size()),
                2,
                RoundingMode.HALF_UP
        );
    }
    public Optional<Employee> findTopEarner() {//поиск самого высокооплачиваемого сотрудника
        return repository.findAll().stream()
                .max(Comparator.comparing(Employee::getSalary));
    }
    public List<Employee> filterByPosition(String position) {//фильтрация сотрудников по должности
        List<Employee> result = new ArrayList<>();
        if (position == null || position.isBlank()) {
            return result;
        }
        for (Employee emp : repository.findAll()) {
            if (position.equalsIgnoreCase(emp.getPosition())) {
                result.add(emp);
            }
        }
        return result;
    }
    public int getEmployeeCount() {//общее количество сотрудников
        int count = 0;
        for (Employee ignored : repository.findAll()) {
            count++;
        }
        return count;
    }
    public List<Employee> sortByHireDate() { //сортировка по дате приема
        List<Employee> employees = new ArrayList<>(repository.findAll());
        employees.sort(Comparator.comparing(Employee::getHireDate));
        return employees;
    }
}
