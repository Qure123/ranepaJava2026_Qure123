package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
        List<Employee> employees = toList(repository.findAll());
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
    public Optional<Employee> findTopEarner() { //поиск самого высокооплачиваемого сотрудника
        List<Employee> employees = toList(repository.findAll());
        if (employees.isEmpty()) {
            return Optional.empty();
        }
        Employee top = employees.get(0);
        for (Employee emp : employees) {
            if (emp.getSalary().compareTo(top.getSalary()) > 0) {
                top = emp;
            }
        }
        return Optional.of(top);
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
    private List<Employee> toList(Iterable<Employee> iterable) {
        List<Employee> list = new ArrayList<>();
        for (Employee emp : iterable) {
            list.add(emp);
        }
        return list;
    }
}
