package ru.ranepa.hrm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ranepa.hrm.model.Employee;

import java.math.BigDecimal;
import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import ru.ranepa.hrm.model.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  List<Employee> findByPosition(String position);

  List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary);
}
