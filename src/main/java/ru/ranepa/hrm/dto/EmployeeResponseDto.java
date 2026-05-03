package ru.ranepa.hrm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeResponseDto {

  public Long id;
  public String name;
  public String position;
  public BigDecimal salary;
  public LocalDate hireDate;

  public EmployeeResponseDto(Long id, String name, String position,
                             BigDecimal salary, LocalDate hireDate) {
    this.id = id;
    this.name = name;
    this.position = position;
    this.salary = salary;
    this.hireDate = hireDate;
  }
}
