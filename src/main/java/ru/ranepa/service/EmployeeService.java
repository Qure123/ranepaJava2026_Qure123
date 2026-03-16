package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public BigDecimal calculateAverageSalary() {
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        BigDecimal sumSalary = BigDecimal.ZERO;
        while (allEmployees.iterator().hasNext()) {
            Employee employee = allEmployees.iterator().next();
            sumSalary = sumSalary.add(employee.getSalary());
        }
        //return sumSalary.divide(allEmployees.);
    }
}
