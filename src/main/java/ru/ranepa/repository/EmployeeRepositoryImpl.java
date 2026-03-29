package ru.ranepa.repository;

import ru.ranepa.model.Employee;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository{
    private final Map<Long, Employee> employees = new HashMap<>();
    private Long nextId = 1L;
    @Override
    public String save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(nextId++);
        }
        employees.put(employee.getId(), employee);
        return "Employee " + employee.getId() + " was saved successfully";
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Iterable<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public String delete(Long id) {
        if (employees.remove(id) != null) {
            return "Employee " + id + " was deleted";
        }
        return "Employee with id " + id + " not found";
    }
}
