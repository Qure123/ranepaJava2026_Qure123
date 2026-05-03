package ru.ranepa.hrm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ru.ranepa.hrm.exception.EmployeeNotFoundException;
import ru.ranepa.hrm.model.Employee;
import ru.ranepa.hrm.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Service
public class EmployeeService {

  private final EmployeeRepository repository;

  public EmployeeService(EmployeeRepository repository) {
    this.repository = repository;
  }

  // CRUD

  public Employee create(Employee employee) {
    return repository.save(employee);
  }

  public Employee getById(Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new EmployeeNotFoundException(id));
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new EmployeeNotFoundException(id);
    }
    repository.deleteById(id);
  }

  // Pageable + сортировка

  public Page<Employee> getAllPaged(int page, int size, String sortBy) {
    Pageable pageable = PageRequest.of(
      page,
      size,
      Sort.by(sortBy).ascending()
    );
    return repository.findAll(pageable);
  }

  public List<Employee> getByPosition(String position) {
    return repository.findByPosition(position);
  }

  public BigDecimal calculateAverageSalary() {
    List<Employee> employees = repository.findAll();

    if (employees.isEmpty()) {
      return BigDecimal.ZERO;
    }

    BigDecimal sum = employees.stream()
      .map(Employee::getSalary)
      .reduce(BigDecimal.ZERO, BigDecimal::add);

    return sum.divide(
      BigDecimal.valueOf(employees.size()),
      2,
      RoundingMode.HALF_UP
    );
  }

  public Employee findTopEarner() {
    return repository.findAll().stream()
      .max(Comparator.comparing(Employee::getSalary))
      .orElse(null); // вместо orElseThrow
  }
}
