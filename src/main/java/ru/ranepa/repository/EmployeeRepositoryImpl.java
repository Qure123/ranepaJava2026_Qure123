package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository{
    private Map<Long, Employee> employees = new HashMap<>();
    private static Long nextId = 1L;
    @Override
    public String save(Employee employee) {
        employee.setId(nextId++);
        employees.put(employee.getId(), employee);
        return "Employee " + employee.getId() + " was saved successfully";
    }

    @Override
    public Optional<Employee> findById(Long id) {

        return Optional.of(employees.get(id));
    }

    @Override
    public Iterable<Employee> findAll() {
        return null;
    }

    @Override
    public String delete(Long id) {
        return "";
    }
}
