package ru.ranepa.repository;

import ru.ranepa.model.Employee;
import java.util.Optional; // Если найдено, то работает. Не найдено - сообщит, обертка для null

public interface EmployeeRepository {
    String save(Employee employee);
    Optional<Employee> findById(Long id);
    Iterable<Employee> findAll();
    String delete(Long id);
}
