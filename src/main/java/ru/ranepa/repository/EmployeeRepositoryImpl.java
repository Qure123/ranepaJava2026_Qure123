package ru.ranepa.repository;

import ru.ranepa.model.Employee;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
    public Collection<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public String delete(Long id) {
        if (employees.remove(id) != null) {
            return "Employee " + id + " was deleted";
        }
        return "Employee with id " + id + " not found";
    }
    @Override
    public String exportToCsv(String filePath) {
        String header = "id,name,position,salary,hireDate\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(header);
            for (Employee emp : employees.values()) {
                String line = String.format("%d,%s,%s,%s,%s\n",
                        emp.getId(),
                        escapeCsv(emp.getName()),
                        escapeCsv(emp.getPosition()),
                        emp.getSalary(),
                        emp.getHireDate()
                );
                writer.write(line);
            }
            return "Exported " + employees.size() + " employees to " + filePath;
        } catch (IOException e) {
            return "Error exporting to CSV: " + e.getMessage();
        }
    }
    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
